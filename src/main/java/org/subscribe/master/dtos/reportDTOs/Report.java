package org.subscribe.master.dtos.reportDTOs;

import org.subscribe.master.enums.Currency;

public class Report {

    private String subscriptionName;

    private Double price;

    private Currency currency;

    private Double priceInMainCurrency;

    private Double totalExpense;

    private Double totalExpenseInMainCurrency;

    public Report(String subscriptionName, Double price, Currency currency, Double priceInMainCurrency, Double totalExpense, Double totalExpenseInMainCurrency) {
        this.subscriptionName = subscriptionName;
        this.price = price;
        this.currency = currency;
        this.priceInMainCurrency = priceInMainCurrency;
        this.totalExpense = totalExpense;
        this.totalExpenseInMainCurrency = totalExpenseInMainCurrency;
    }

    public Report() {
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

    public Double getPriceInMainCurrency() {
        return priceInMainCurrency;
    }

    public void setPriceInMainCurrency(Double priceInMainCurrency) {
        this.priceInMainCurrency = priceInMainCurrency;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double annuallyExpense) {
        this.totalExpense = annuallyExpense;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getTotalExpenseInMainCurrency() {
        return totalExpenseInMainCurrency;
    }

    public void setTotalExpenseInMainCurrency(Double totalExpenseInMainCurrency) {
        this.totalExpenseInMainCurrency = totalExpenseInMainCurrency;
    }
}
