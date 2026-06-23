package org.subscribe.master.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_subscriptons")
@EntityListeners(AuditingEntityListener.class)
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Enumerated(EnumType.STRING)
    private SubscriptionType type;

    @CreatedDate
    private LocalDateTime subscriptionStartedDate;

    private LocalDateTime nextPaymentDate;


    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_id")
    //TODO: CHEckkkkkkkkkkkkkkkkkkkkkkkkk
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscriber_id")
    //TODO: CHEckkkkkkkkkkkkkkkkkkkkkkkkk
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AuthUser subscriber;

    public UserSubscription() {
    }

    public UserSubscription(SubscriptionStatus status, SubscriptionType type, LocalDateTime nextPaymentDate, Boolean isDeleted, Subscription subscription, AuthUser subscriber) {
        this.status = status;
        this.type = type;
        this.nextPaymentDate = nextPaymentDate;
        this.isDeleted = isDeleted;
        this.subscription = subscription;
        this.subscriber = subscriber;
    }

    public Long getId() {
        return id;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public LocalDateTime getSubscriptionStartedDate() {
        return subscriptionStartedDate;
    }

    public LocalDateTime getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDateTime nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    @Override
    public String toString() {
        return "UserSubscription{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", subscriptionStartedDate=" + subscriptionStartedDate +
                ", nextPaymentDate=" + nextPaymentDate +
                ", isDeleted=" + isDeleted +
                ", subscription=" + subscription.getName() +
                ", subscriber=" + subscriber.getUsername() +
                '}';
    }
}
