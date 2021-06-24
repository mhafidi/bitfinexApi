package com.bitfinex.services.bitfinex_rest_api;

public interface IRestService
{
    String getCandles(String candleInterval,String symbol);
    String getCandle(String candleInterval,String symbol);
}
