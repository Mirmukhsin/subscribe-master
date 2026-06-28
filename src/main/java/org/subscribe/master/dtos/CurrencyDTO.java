package org.subscribe.master.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrencyDTO(
        @JsonProperty("Ccy") String ccy,
        @JsonProperty("Rate") String rate
) {
}
