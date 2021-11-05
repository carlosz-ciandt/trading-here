package com.trading.tradinghere.config

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

abstract class UnitSpec extends Specification {

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.trading.tradinghere.templates")
    }
}