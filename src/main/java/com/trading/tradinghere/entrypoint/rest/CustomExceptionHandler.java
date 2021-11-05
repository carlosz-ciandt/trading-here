package com.trading.tradinghere.entrypoint.rest;

import com.trading.tradinghere.entrypoint.rest.json.ErrorResponse;
import com.trading.tradinghere.entrypoint.rest.json.error.ErrorDetailResponse;
import com.trading.tradinghere.entrypoint.rest.json.error.ErrorField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> processException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        System.out.println(parameterName + " parameter is missing");

        ErrorField requiredParameter = ErrorField.builder()
                .code("required_parameter")
                .message(format("'%s' parameter is missing", parameterName))
                .field(parameterName)
                .build();

        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code("required_parameter")
                .message("Required fields message")
                .errorField(requiredParameter)
                .build();

        return this.createResponseEntity(HttpStatus.BAD_REQUEST, errorDetailResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> processException(Exception ex) {
        log.error("An unexpected exception occurred: {}", ex.getMessage(), ex);

        ErrorDetailResponse internalServerError = ErrorDetailResponse.builder()
                .code("internal_server_error")
                .message(format("error processing request: %s", ex.getMessage()))
                .build();

        return createResponseEntity(INTERNAL_SERVER_ERROR, internalServerError);
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(HttpStatus status, ErrorDetailResponse errorDetailResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.status(status)
                .headers(headers)
                .body(ErrorResponse.builder()
                        .t(errorDetailResponse)
                        .build());
    }
}
