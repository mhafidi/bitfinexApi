package com.bitfinex.core;

import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MainControllerService
{

    private static final Logger logger = LoggerFactory.getLogger(MainControllerService.class);
    private final IRestService iRestService;
    private final int CORE_POOL_SIZE=Integer.MAX_VALUE;
    ExecutorService mainThreadService = Executors.newFixedThreadPool(CORE_POOL_SIZE);
    StrategyHandler strategyHandler;


    @Autowired
    public MainControllerService(IRestService iRestService) {
        this.iRestService = iRestService;
    }


    public String startStrategy(String strategyType,String symbol) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("StrategyType ["+strategyType+"] Symbol ["+symbol+"]");
        }

        if(strategyHandler!=null)
        {
            return "Trading BOT is already started";
        }
        strategyHandler = new StrategyHandler(iRestService, StrategyType.valueOf(strategyType),symbol);
        mainThreadService.submit(strategyHandler);
        return "started";
    }

    public String stopStrategy()
    {
        strategyHandler.stop();
        strategyHandler=null;
        return "stopped";
    }
}
