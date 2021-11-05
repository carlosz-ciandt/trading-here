package com.trading.tradinghere.entrypoint.rest

import com.trading.tradinghere.config.AbstractHttpIT
import com.trading.tradinghere.domain.Greeting
import com.trading.tradinghere.domain.Hello
import com.trading.tradinghere.usecase.SayHello
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest([HelloController.class])
class HelloControllerITSpecification extends AbstractHttpIT {

    private MockMvc mockMvc

    @SpringBean
    SayHello sayHello = Mock()

    def setup() {
        mockMvc = super.buildMockMvcWithBusinessExceptionHandler()
    }

    def "should be a OK status when send request parameter name"() {
        given:
        String name = 'Carlos'

        Hello hello = Hello.builder()
                .name(name)
                .build()
        Greeting greeting = Greeting.builder()
                .id(1L)
                .uuid(UUID.fromString('589edadf-6d72-4371-b925-e67c54f4be69'))
                .phrase('Hello world my friend Carlos')
                .build()

        1 * this.sayHello.execute(hello) >> greeting

        when:
        ResultActions result = mockMvc.perform(get("/api/v1/say-hello?name=$name"))

        then:
        result
        result.andExpect(status().isOk())

        and:
        result.andExpect(jsonPath('$.data.phrase').value('Hello world my friend Carlos'))
    }

    def "should be a BAD REQUEST status when don't send request parameter name"() {
        given:
        0 * this.sayHello.execute(_) >> _

        when:
        ResultActions result = mockMvc.perform(get("/api/v1/say-hello"))

        then:
        result
        result.andExpect(status().isBadRequest())

        and:
        result.andExpect(jsonPath('$.error.code').value('required_parameter'))
        result.andExpect(jsonPath('$.error.message').value("Required fields message"))
        result.andExpect(jsonPath('$.error.errorFields.length()').value(1))
        result.andExpect(jsonPath('$.error.errorFields[0].code').value('required_parameter'))
        result.andExpect(jsonPath('$.error.errorFields[0].message').value("'name' parameter is missing"))
        result.andExpect(jsonPath('$.error.errorFields[0].field').value('name'))
    }

    def "should be a INTERNAL SERVER ERROR status when some not error mapped occurs"() {
        given:
        String name = 'Carlos'

        Hello hello = Hello.builder()
                .name(name)
                .build()
        Greeting greeting = Greeting.builder()
                .id(1L)
                .uuid(UUID.fromString('589edadf-6d72-4371-b925-e67c54f4be69'))
                .phrase('Hello world my friend Carlos')
                .build()

        1 * this.sayHello.execute(hello) >> { Hello helloParameter ->
            throw new Exception("Something is not right")
        }

        when:
        ResultActions result = mockMvc.perform(get("/api/v1/say-hello?name=$name"))

        then:
        result
        result.andExpect(status().isInternalServerError())

        and:
        result.andExpect(jsonPath('$.error.code').value('internal_server_error'))
        result.andExpect(jsonPath('$.error.message').value("error processing request: Something is not right"))
        result.andExpect(jsonPath('$.error.errorFields').doesNotExist())
    }
}
