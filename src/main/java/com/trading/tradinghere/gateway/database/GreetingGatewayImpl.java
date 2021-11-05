package com.trading.tradinghere.gateway.database;

import com.trading.tradinghere.domain.Greeting;
import com.trading.tradinghere.gateway.GreetingGateway;
import com.trading.tradinghere.gateway.database.entity.GreetingEntity;
import com.trading.tradinghere.gateway.database.mapper.GreetingEntityMapper;
import com.trading.tradinghere.gateway.database.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GreetingGatewayImpl implements GreetingGateway {

    private final GreetingRepository greetingRepository;

    @Autowired
    public GreetingGatewayImpl(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @Override
    public Greeting register(Greeting greeting) {
        GreetingEntity greetingEntity = GreetingEntityMapper.INSTANCE
                .mapFromGreeting(greeting);

        GreetingEntity savedGreetingEntity = this.greetingRepository.save(greetingEntity);

        Greeting savedGreeting = GreetingEntityMapper.INSTANCE.mapToGreeting(savedGreetingEntity);

        return savedGreeting;
    }
}
