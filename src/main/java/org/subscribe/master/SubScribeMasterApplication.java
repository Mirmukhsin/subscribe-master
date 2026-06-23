package org.subscribe.master;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.subscribe.master.entities.Subscription;
import org.subscribe.master.enums.Currency;
import org.subscribe.master.repositories.SubscriptionRepository;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class SubScribeMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubScribeMasterApplication.class, args);
    }


    @Bean
    public CommandLineRunner runner(SubscriptionRepository subscriptionRepository) {
        return args -> {
            subscriptionRepository.saveAll(List.of(
                    new Subscription("ChatGPT", 20.0, Currency.USD, "AI"),
                    new Subscription("Claude.AI", 15.0, Currency.USD, "AI"),
                    new Subscription("YouTube Premium", 30.0, Currency.USD, "Strimming"),
                    new Subscription("Netflix", 25.0, Currency.USD, "Strimming"),
                    new Subscription("Udemy", 9.0, Currency.USD, "Education"),
                    new Subscription("Spotify", 10.0, Currency.USD, "Music")
            ));
        };
    }

}
