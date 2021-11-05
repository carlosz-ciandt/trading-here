package com.trading.tradinghere.entrypoint.rest.json.hello;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HelloResponse {

    private String phrase;

}
