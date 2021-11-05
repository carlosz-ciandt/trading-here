package com.trading.tradinghere.config

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.trading.tradinghere.config.database.DatabaseConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.images.AbstractImagePullPolicy
import org.testcontainers.images.ImageData
import org.testcontainers.images.PullPolicy
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@SpringBootTest(classes = [
        DatabaseTestConfiguration.class, DatabaseConfiguration.class
])
@Sql(scripts = "classpath:db/clean_tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("integration-test")
abstract class AbstractRepositoryIT extends Specification {

    static PostgreSQLContainer postgreSQLContainer

    def setupSpec() {
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

        System.setProperty("spring.datasource.url.container", postgreSQLContainer.getJdbcUrl())
        System.setProperty("spring.datasource.username.container", postgreSQLContainer.getUsername())
        System.setProperty("spring.datasource.password.container", postgreSQLContainer.getPassword())
    }

    def cleanupSpec() {
        postgreSQLContainer.stop()
    }
}
