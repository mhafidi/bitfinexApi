package com.bitfinex.core;

import com.bitfinex.dao.Candle;
import com.bitfinex.dao.Candles;
import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class MainControllerService {

    private static final Logger readerLogger = LoggerFactory.getLogger(MainControllerService.class);
    private final IRestService iRestService;


    @Autowired
    public MainControllerService(IRestService iRestService) {
        this.iRestService = iRestService;
    }


    public String startStrategy(String algoType,String symbol) throws DAOException
    {

        String payload=iRestService.getCandle(CandleInterval.MINUTE_1.toString(),symbol);
        String payload2=iRestService.getCandles(CandleInterval.MINUTE_1.toString(),symbol);
        System.out.println(payload2);
        Candle candle = new Candle(payload);
        Candles candles = new Candles(payload2);
        System.out.println(candles.getCandles().size());

        return candle.toString();
    }
}
