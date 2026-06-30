package org.subscribe.master.utility.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.subscribe.master.dtos.UserSubscriptionsResponseDTO;
import org.subscribe.master.entities.UserSubscription;

@Mapper(componentModel = "spring")
public interface UserSubscriptionMapper {

    @Mapping(target = "subscriptionName", source = "subscription.name")
    @Mapping(target = "price", source = "subscription.price")
    @Mapping(target = "currency", source = "subscription.currency")
    @Mapping(target = "category", source = "subscription.category")
    UserSubscriptionsResponseDTO userSubToDTO(UserSubscription userSubscription);
}
