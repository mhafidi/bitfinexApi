package com.bitfinex.core;

import com.bitfinex.dao.Candle;
import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainControllerService {

    private final IRestService iRestService;

    @Autowired
    public MainControllerService(IRestService iRestService) {
        this.iRestService = iRestService;
    }


    public String startStrategy() throws DAOException {
        String payload=iRestService.getCandle(CandleInterval.MINUTE_1.toString(),"tETHUSD").toString();
        Candle candle = new Candle(payload);

        return candle.toString();
    }
}
