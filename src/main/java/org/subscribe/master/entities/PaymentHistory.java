package org.subscribe.master.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_histories")
@EntityListeners(AuditingEntityListener.class)
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime paymentDate;

    private String reason;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private AuthUser subscriber;

    public PaymentHistory() {
    }

    public PaymentHistory(Subscription subscription, AuthUser subscriber, String reason, Double amount) {
        this.subscription = subscription;
        this.subscriber = subscriber;
        this.reason = reason;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public AuthUser getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(AuthUser subscriber) {
        this.subscriber = subscriber;
    }

}
