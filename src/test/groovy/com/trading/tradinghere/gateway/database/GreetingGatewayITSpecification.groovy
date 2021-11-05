package com.trading.tradinghere.gateway.database

import com.trading.tradinghere.config.AbstractRepositoryIT
import com.trading.tradinghere.domain.Greeting
import com.trading.tradinghere.gateway.GreetingGateway
import com.trading.tradinghere.gateway.database.repository.GreetingRepository
import org.springframework.beans.factory.annotation.Autowired

class GreetingGatewayITSpecification extends AbstractRepositoryIT {

    @Autowired
    GreetingGateway greetingGateway

    @Autowired
    GreetingRepository greetingRepository

    def "should be saved a greeting entity"() {
        given:
        def greeting = Greeting.builder()
                .uuid(UUID.fromString('589edadf-6d72-4371-b925-e67c54f4be69'))
                .phrase('Hello world my friend Carlos')
                .build()

        when:
        def register = this.greetingGateway.register(greeting)

        then:
        def greetingEntity = greetingRepository.findByUuid(register.uuid)
        greetingEntity != null
        greetingEntity.id != null
        greetingEntity.uuid == UUID.fromString('589edadf-6d72-4371-b925-e67c54f4be69')
        greetingEntity.phrase == 'Hello world my friend Carlos'
    }
}
