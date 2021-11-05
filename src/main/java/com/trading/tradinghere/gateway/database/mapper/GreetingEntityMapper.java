package com.trading.tradinghere.gateway.database.mapper;

import com.trading.tradinghere.domain.Greeting;
import com.trading.tradinghere.gateway.database.entity.GreetingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public abstract class GreetingEntityMapper {

    public final static GreetingEntityMapper INSTANCE = Mappers.getMapper(GreetingEntityMapper.class);

    public abstract GreetingEntity mapFromGreeting(Greeting greeting);

    public abstract Greeting mapToGreeting(GreetingEntity savedGreetingEntity);

//    public String mapFromUuid(UUID uuid) {
//        return uuid != null ? uuid.toString() : null;
//    }
//
//    public UUID mapToUuid(String uuid) {
//        return uuid != null ? UUID.fromString(uuid) : null;
//    }
}
