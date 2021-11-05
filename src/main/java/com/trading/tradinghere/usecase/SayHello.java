package com.trading.tradinghere.usecase;

import com.trading.tradinghere.config.log.LogKey;
import com.trading.tradinghere.domain.Greeting;
import com.trading.tradinghere.domain.Hello;
import com.trading.tradinghere.gateway.GreetingGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static java.lang.String.format;
import static net.logstash.logback.argument.StructuredArguments.v;

@Slf4j
@Component
public class SayHello {

    private final GreetingGateway helloGateway;

    @Autowired
    public SayHello(GreetingGateway helloGateway) {
        this.helloGateway = helloGateway;
    }

    public Greeting execute(Hello hello) {
        Objects.requireNonNull(hello, "Hello cannot be null");

        Greeting greeting = Greeting.builder()
                .uuid(UUID.randomUUID())
                .phrase(format("Hello world my friend %s", hello.getName()))
                .build();

        log.info("Say hello {} to greeting {}",
                v(LogKey.HELLO.toString(), hello),
                v(LogKey.GREETING.toString(), greeting));

        Greeting greetingRegistered = helloGateway.register(greeting);

        log.info("Greeting {} registered",
                v(LogKey.GREETING.toString(), greetingRegistered));

        return greetingRegistered;
    }
}
