package com.trading.tradinghere.usecase

import com.trading.tradinghere.config.UnitSpec
import com.trading.tradinghere.domain.Greeting
import com.trading.tradinghere.domain.Hello
import com.trading.tradinghere.gateway.GreetingGateway

class SayHelloSpecification extends UnitSpec {

    def "should be a NullPointerException when hello is null"() {
        given:
        GreetingGateway helloGateway = Mock(GreetingGateway)
        SayHello sayHello = new SayHello(helloGateway)

        when:
        def greeting = sayHello.execute()

        then:
        def exception = thrown(NullPointerException)
        exception.message == 'Hello cannot be null'
    }

    def "should be a greeting"() {
        given:
        GreetingGateway helloGateway = Mock(GreetingGateway)
        SayHello sayHello = new SayHello(helloGateway)

        Hello hello = Hello.builder()
                .name("Carlos")
                .build()

        1 * helloGateway.register(_) >> {
            return Greeting.builder()
                    .id(1L)
                    .uuid(it[0].uuid)
                    .phrase(it[0].phrase)
                    .build()
        }

        when:
        def greeting = sayHello.execute(hello)

        then:
        greeting.id != null
        greeting.uuid != null
        greeting.phrase == 'Hello world my friend Carlos'
    }
}
