package com.trading.tradinghere.entrypoint.rest.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(value = PRIVATE)
@Builder(toBuilder = true)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class ErrorResponse<T> implements Serializable {

    private static final long serialVersionUID = 7998454850553963931L;

    @JsonProperty("error")
    private T t;

}
