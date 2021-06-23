package com.bitfinex.services.bitfinex_rest_api;

import com.bitfinex.dao.Candle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BitfinexRestImpl implements IRestService {


    private final BitfinexClient bitfinexClient;

    @Autowired
    public BitfinexRestImpl(BitfinexClient bitfinexClient) {
        this.bitfinexClient = bitfinexClient;
    }

    @Override
    public List<Candle> getCandles(String candleInterval, String symbol) {
        return bitfinexClient.getCandles(candleInterval,symbol);
    }

    @Override
    public String getCandle(String candleInterval, String symbol)
    {

        return bitfinexClient.getCandle(candleInterval,symbol);
    }
}
