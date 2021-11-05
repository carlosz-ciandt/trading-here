package com.trading.tradinghere.entrypoint.rest.json.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "message" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorField {

    private String code;
    private String message;
    private String field;

}
