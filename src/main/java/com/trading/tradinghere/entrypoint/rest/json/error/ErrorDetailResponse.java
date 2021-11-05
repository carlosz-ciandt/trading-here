package com.trading.tradinghere.entrypoint.rest.json.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(value = PRIVATE)
@Builder(toBuilder = true)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDetailResponse implements Serializable {

    private static final long serialVersionUID = -2217238903424097390L;

    private String code;
    private String message;

    @Singular
    private List<ErrorField> errorFields;

}
