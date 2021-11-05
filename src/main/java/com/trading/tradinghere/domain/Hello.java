package com.trading.tradinghere.domain;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Hello {

    private String name;

}
