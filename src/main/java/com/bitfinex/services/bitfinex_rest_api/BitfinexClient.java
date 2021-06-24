package com.bitfinex.services.bitfinex_rest_api;
import com.bitfinex.dao.Candle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name="Bitfinex", url = "https://api-pub.bitfinex.com/v2")
public interface BitfinexClient {


    @RequestMapping(path = "candles/trade:{candleInterval}:{symbol}/hist/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    String getCandles(@PathVariable("candleInterval") String candleInterval, @PathVariable("symbol") String symbol);

    @RequestMapping(path = "candles/trade:{candleInterval}:{symbol}/last/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    String getCandle(@PathVariable("candleInterval") String candleInterval, @PathVariable("symbol") String symbol);


}
