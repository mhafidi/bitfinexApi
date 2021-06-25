package com.bitfinex.core;

import com.bitfinex.core.strategies.StrategyAlgorithm;
import com.bitfinex.core.strategies.MovingAverageStrategy;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StrategyHandler implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(StrategyHandler.class);
    StrategyAlgorithm strategyAlgorithm;
    IRestService iRestService;
    StrategyType strategyType;
    String symbol;
    private final int CORE_POOL_SIZE=Integer.MAX_VALUE;
    ExecutorService mainThreadService = Executors.newFixedThreadPool(CORE_POOL_SIZE);
    public StrategyHandler(IRestService iRestService, StrategyType strategyType, String symbol)
    {
        this.iRestService = iRestService;
        this.strategyType = strategyType;
        this.symbol = symbol;
    }

    @Override
    public void run()
    {

        switch (strategyType)
        {
            case moving_avg:
                logger.info("Starting Moving average strategy");
                strategyAlgorithm = new MovingAverageStrategy(iRestService,symbol);
                mainThreadService.submit(strategyAlgorithm);
                break;
            case ADX_contraction:
                logger.warn("["+StrategyType.ADX_contraction+"] is not supported");
                break;
        }



    }



    public void stop()
    {
        if(strategyAlgorithm !=null)
            strategyAlgorithm.stop();
    }
}
