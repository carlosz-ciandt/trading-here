package com.trading.tradinghere.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import spock.lang.Specification

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.trading.tradinghere.gateway.database")
//@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration.class])
class DatabaseTestConfiguration extends Specification {

}
