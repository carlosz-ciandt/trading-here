package com.trading.tradinghere.config

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.trading.tradinghere.config.jackson.ObjectMapperConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@ActiveProfiles("integration-test")
@Import(value = [ ObjectMapperConfiguration.class])
abstract class AbstractHttpIT extends Specification {

    @Autowired
    private WebApplicationContext webApplicationContext

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.trading.tradinghere.templates")
    }

    protected MockMvc buildMockMvcWithBusinessExceptionHandler() {
        return MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build()
    }
}
