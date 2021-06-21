package com.bitfinex.services.bitfinex_rest_api;

import com.bitfinex.dao.Candle;

import java.util.List;

public interface IRestService
{
    List<Candle> getCandles(String candleInterval,String symbol);
    Candle getCandle(String candleInterval,String symbol);
}
