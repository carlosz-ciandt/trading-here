package com.trading.tradinghere.entrypoint.rest.mapper;

import com.trading.tradinghere.domain.Greeting;
import com.trading.tradinghere.domain.Hello;
import com.trading.tradinghere.entrypoint.rest.json.hello.HelloResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class HelloResponseMapper {

    public final static HelloResponseMapper INSTANCE = Mappers.getMapper(HelloResponseMapper.class);

    public abstract Hello mapToHello(String name);

    public abstract HelloResponse mapToHelloResponse(Greeting greeting);
}
