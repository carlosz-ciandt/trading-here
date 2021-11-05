package com.trading.tradinghere.config

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.trading.tradinghere.config.feign.FeignConfiguration
import com.trading.tradinghere.config.resilience.ResilienceConfiguration
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration
import io.github.resilience4j.circuitbreaker.configure.CircuitBreakerConfiguration
import lombok.extern.slf4j.Slf4j
import org.junit.BeforeClass
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
import spock.lang.Specification

import static java.lang.String.format

@Slf4j
@SpringBootTest(classes = [ /*FeignClientTestConfiguration.class,*/ FeignConfiguration.class, ResilienceConfiguration.class,
        CircuitBreakerConfiguration.class ])
@ImportAutoConfiguration([ FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class,
        CircuitBreakerAutoConfiguration.class, AopAutoConfiguration.class ])
@TestPropertySource(locations = [ "classpath:application-integration-test.yml" ])
@ActiveProfiles("integration-test")
abstract class AbstractFeignClientIT extends Specification {

    @Shared
    public static WireMockServer wireMockServer

    @BeforeClass
    static void setUpBeforeClass() throws Exception {
        FixtureFactoryLoader.loadTemplates("com.trading.tradinghere.templates")
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort())
            wireMockServer.start()
        }

        System.setProperty("wiremock.client.config.endpoint", format("http://localhost:%s", wireMockServer.port()))
    }
}
