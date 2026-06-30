package org.subscribe.master.services.notificationService.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.UserSubsNotificationDTO;
import org.subscribe.master.repositories.UserSubscriptionRepository;
import org.subscribe.master.services.notificationService.NotificationService;
import org.subscribe.master.utility.CurrencyConverter;
import org.subscribe.master.utility.SecurityUtility;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailNotifier implements NotificationService {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final CurrencyConverter currencyConverter;
    private final SecurityUtility securityUtility;

    public EmailNotifier(UserSubscriptionRepository userSubscriptionRepository, CurrencyConverter currencyConverter, SecurityUtility securityUtility) {
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.currencyConverter = currencyConverter;
        this.securityUtility = securityUtility;
    }

    @Override
    public void sendNotification(List<UserSubsNotificationDTO> notificationDTOS) {
        Map<String, List<UserSubsNotificationDTO>> listMap = notificationDTOS.stream()
                .filter(dto -> dto.email().equals(securityUtility.getCurrentUserEmail()))
                .map(dto ->
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
    @Scheduled(cron = "0 * * * * *")
//    @Scheduled(initialDelay = 20, fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void checkExpiration() {
        List<UserSubsNotificationDTO> expiredInDays = userSubscriptionRepository
                .findExpiredInDays(LocalDate.now().plusDays(2).atStartOfDay(), LocalDate.now().plusDays(3).atStartOfDay());

        if (!expiredInDays.isEmpty()) {
            sendNotification(expiredInDays);
        }
    }
}
