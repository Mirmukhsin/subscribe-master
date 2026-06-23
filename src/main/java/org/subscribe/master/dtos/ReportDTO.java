package org.subscribe.master.dtos;

import org.subscribe.master.enums.Currency;

public class ReportDTO {
    private String subscriptionName;

    private Double price;

    private Currency currency;

    private Double amount;

    public ReportDTO() {
    }

    public ReportDTO(String subscriptionName, Double price, Currency currency, Double amount) {
        this.subscriptionName = subscriptionName;
        this.price = price;
        this.currency = currency;
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
