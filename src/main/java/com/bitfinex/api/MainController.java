package com.bitfinex.api;


import com.bitfinex.core.MainControllerService;
import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/main-control")
public class MainController
{
    private static final Logger mainControllerLogger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    MainControllerService mainControllerService;

    @GetMapping("/startBot/symbols/{symbol}/algos/{strategyType}")
    public String startBot(@PathVariable(value="symbol")String symbol,@PathVariable(value="strategyType") String strategyType) throws DAOException
    {
        mainControllerLogger.info("strategyType="+strategyType+" symbol="+symbol);
        return mainControllerService.startStrategy(strategyType,symbol, CandleInterval.MINUTE_1);
    }
    @GetMapping("/stopBot")
    public String stopBot()
    {

        return mainControllerService.stopStrategy();
    }
    @GetMapping("/getaccountbalance")
    public double getAccountBalance()
    {

        return mainControllerService.getAccountBalanceValue();
    }

}
