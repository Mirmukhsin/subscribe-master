package org.subscribe.master.dtos.reportDTOs;

import org.subscribe.master.enums.Currency;

public class ReportDTO {
    private String subscriptionName;

    private Double price;

    private Currency currency;

    private Double amountInUZS;

    public ReportDTO() {
    }

    public ReportDTO(String subscriptionName, Double price, Currency currency, Double amountInUZS) {
        this.subscriptionName = subscriptionName;
        this.price = price;
        this.currency = currency;
        this.amountInUZS = amountInUZS;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAmountInUZS() {
        return amountInUZS;
    }

    public void setAmountInUZS(Double amountInUZS) {
        this.amountInUZS = amountInUZS;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "subscriptionName='" + subscriptionName + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", amount=" + amountInUZS +
                '}';
    }
}
