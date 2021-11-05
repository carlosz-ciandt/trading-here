package com.trading.tradinghere.config

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.fasterxml.jackson.databind.ObjectMapper
import com.trading.tradinghere.TradingHereApplication
import io.restassured.RestAssured
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.images.AbstractImagePullPolicy
import org.testcontainers.images.ImageData
import org.testcontainers.images.PullPolicy
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = TradingHereApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("component-test")
abstract class AbstractComponentTest extends Specification {

    @LocalServerPort
    protected int port

    @Autowired
    private ObjectMapper objectMapper

    static PostgreSQLContainer postgreSQLContainer

    def setupSpec() {
        RestAssured.port = this.port

        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:11.2-alpine")
                .withImagePullPolicy(new AbstractImagePullPolicy() {
                    @Override
                    protected boolean shouldPullCached(DockerImageName imageName, ImageData localImageData) {
                        return false
                    }
                })
                .withImagePullPolicy(PullPolicy.defaultPolicy())
                .withExposedPorts(5432)

        postgreSQLContainer.start()

        FixtureFactoryLoader.loadTemplates("com.trading.tradinghere.templates")

        System.setProperty("spring.datasource.tc.url.container", postgreSQLContainer.getJdbcUrl())
        System.setProperty("spring.datasource.tc.username.container", postgreSQLContainer.getUsername())
        System.setProperty("spring.datasource.tc.password.container", postgreSQLContainer.getPassword())
    }

    def setup() {
        RestAssured.port = this.port
    }

    def cleanupSpec() {
        postgreSQLContainer.stop()
    }

    protected String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj)
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }
}
