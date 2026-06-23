package org.subscribe.master.entities;

public class Report {

    private String subscriptionName;

    private Double price;

    private Double priceInMainCurrency;

    private Double annuallyExpense;

    public Report(String subscriptionName, Double price, Double priceInMainCurrency, Double annuallyExpense) {
        this.subscriptionName = subscriptionName;
        this.price = price;
        this.priceInMainCurrency = priceInMainCurrency;
        this.annuallyExpense = annuallyExpense;
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

    public Double getAnnuallyExpense() {
        return annuallyExpense;
    }

    public void setAnnuallyExpense(Double annuallyExpense) {
        this.annuallyExpense = annuallyExpense;
    }
}
