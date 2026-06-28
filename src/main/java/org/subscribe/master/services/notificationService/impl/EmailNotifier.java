package org.subscribe.master.services.notificationService.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.UserSubsNotificationDTO;
import org.subscribe.master.repositories.UserSubscriptionRepository;
import org.subscribe.master.services.notificationService.NotificationService;
import org.subscribe.master.utility.CurrencyConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailNotifier implements NotificationService {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final CurrencyConverter currencyConverter;

    public EmailNotifier(UserSubscriptionRepository userSubscriptionRepository, CurrencyConverter currencyConverter) {
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void sendNotification(List<UserSubsNotificationDTO> notificationDTOS) {
        Map<String, List<UserSubsNotificationDTO>> listMap = notificationDTOS.stream().map(dto ->
                new UserSubsNotificationDTO(
                        dto.username(),
                        dto.email(),
                        dto.subName(),
                        dto.currency(),
                        dto.price(),
                        currencyConverter.convertToUZS(dto.price(), dto.currency()),
                        dto.startedDate(),
                        dto.nextPaymentDate(),
                        dto.type()
                )).collect(Collectors.groupingBy(UserSubsNotificationDTO::email));

        listMap.forEach((email, details) -> System.err.println(email + " = " + details));
    }

    @Override
//    @Scheduled(cron = "/10 * * * * *")
//    @Scheduled(initialDelay = 20, fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void checkExpiration() {
        List<UserSubsNotificationDTO> expiredInDays = userSubscriptionRepository
                .findExpiredInDays(LocalDate.now().plusDays(2).atStartOfDay(), LocalDate.now().plusDays(3).atStartOfDay());

        if (!expiredInDays.isEmpty()) {
            sendNotification(expiredInDays);
        }
    }
}
