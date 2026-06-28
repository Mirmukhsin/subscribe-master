package org.subscribe.master.utility;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.subscribe.master.dtos.CurrencyDTO;
import org.subscribe.master.enums.Currency;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;

import java.util.Arrays;

@Service
public class CurrencyService {

    private final RestClient restClient;

    private static final String BASE_URL = "https://cbu.uz/uz/arkhiv-kursov-valyut/json/";

    public CurrencyService(RestClient restClient) {
        this.restClient = restClient;
    }


    @Cacheable(value = "exchange-rates", key = "#currency")
    public Double getRateToUzs(Currency currency) {

        CurrencyDTO[] rates = restClient.get()
                .uri(BASE_URL)
                .retrieve()
                .body(CurrencyDTO[].class);

        return Arrays.stream(rates)
                .filter(currencyDTO -> currencyDTO.ccy().equals(currency.name()))
                .map(currencyDTO -> Double.parseDouble(currencyDTO.rate()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Rate not found for currency: " + currency.name()));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "exchange-rates", allEntries = true)
    public void evictRatesCache() {
        System.out.println("Exchange rate cache cleared!!!");
    }
}
