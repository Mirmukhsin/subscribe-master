package org.subscribe.master.utility;

import org.springframework.stereotype.Component;
import org.subscribe.master.enums.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyConverter {

    private final CurrencyService currencyService;

    public CurrencyConverter(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public Double convertToUZS(Double amount, Currency currency) {

        if (currency == Currency.UZS) {
            return BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }

        Double rate = currencyService.getRateToUzs(currency);

        return BigDecimal.valueOf(amount * rate).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
