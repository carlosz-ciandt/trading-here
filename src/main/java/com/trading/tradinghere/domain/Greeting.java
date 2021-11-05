package com.trading.tradinghere.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"id", "uuid"})
public class Greeting {

    private Long id;
    private UUID uuid;
    private String phrase;

}
