package com.trading.tradinghere.entrypoint.rest.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(value = PRIVATE)
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataResponse<T> implements Serializable {

    private static final long serialVersionUID = -5528369951169246978L;

    @JsonProperty("data")
    private T t;

}
