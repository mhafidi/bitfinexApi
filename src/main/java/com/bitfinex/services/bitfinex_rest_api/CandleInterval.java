package com.bitfinex.services.bitfinex_rest_api;

public enum CandleInterval {
//1m, 5m, 15m, 30m, 1h, 3h, 6h, 12h, 1D, 7D, 14D, 1M;
    MINUTE_1("1m"),
    MINUTE_5("5m"),
    MINUTE_15("15m"),
    MINUTE_30("30m"),
    HOUR_1("1h"),
    HOUR_3("3h"),
    HOUR_6("6h"),
    DAY_1("1D"),
    MONTH_1("1M");

    String candleInterval;

    CandleInterval(String aInCandleInterval)
    {
        candleInterval=aInCandleInterval;

    }

    @Override
    public String toString() {
        return candleInterval;
    }
}
