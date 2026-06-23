package org.subscribe.master.utility;

import org.springframework.data.jpa.domain.Specification;
import org.subscribe.master.entities.Subscription;
import org.subscribe.master.enums.Currency;

public class SubscriptionSpecification {

    public static Specification<Subscription> hasCurrency(Currency currency) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("currency"), currency);
    }

    public static Specification<Subscription> hasPriceGreaterThan(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), price);
    }

    public static Specification<Subscription> hasPriceLessThan(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), price);
    }
}
