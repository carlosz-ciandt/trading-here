package com.trading.tradinghere.entrypoint.rest;

import com.trading.tradinghere.config.log.LogKey;
import com.trading.tradinghere.domain.Greeting;
import com.trading.tradinghere.domain.Hello;
import com.trading.tradinghere.entrypoint.rest.json.DataResponse;
import com.trading.tradinghere.entrypoint.rest.json.hello.HelloResponse;
import com.trading.tradinghere.entrypoint.rest.mapper.HelloResponseMapper;
import com.trading.tradinghere.usecase.SayHello;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.argument.StructuredArguments.v;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
public class HelloController {

    private final SayHello sayHello;

    @Autowired
    public HelloController(SayHello sayHello) {
        this.sayHello = sayHello;
    }

    @ApiOperation(value = "Get Health Contracted Plans by Proposal Number.")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Health Contracted Plans found"),
                    @ApiResponse(code = 400, message = "Invalid parameters"),
                    @ApiResponse(code = 500, message = "Internal server error")
            }
    )
    @GetMapping(path = "/v1/say-hello", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<HelloResponse>> sayHello(@RequestParam("name") String name) {

        log.info("Say hello with name: [{}].",
                v(LogKey.NAME.toString(), name));

        Hello hello = HelloResponseMapper.INSTANCE.mapToHello(name);
        Greeting greeting = this.sayHello.execute(hello);

        HelloResponse helloResponse = HelloResponseMapper.INSTANCE
                .mapToHelloResponse(greeting);

        log.info("Get greetings from use case [{}] returned success.",
                v(LogKey.GREETING.toString(), greeting));

        return ResponseEntity.ok()
                .body(DataResponse.<HelloResponse>builder()
                        .t(helloResponse)
                        .build());
    }
}
