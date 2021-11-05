package com.trading.tradinghere.config.log;

import com.google.common.base.CaseFormat;

public enum LogKey {

    EXCEPTION_TYPE_NAME,
    EXCEPTION_CODE,
    EXCEPTION_MESSAGE,
    NAME,
    HELLO,
    GREETING;

    @Override
    public String toString() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name().toLowerCase());
    }
}
