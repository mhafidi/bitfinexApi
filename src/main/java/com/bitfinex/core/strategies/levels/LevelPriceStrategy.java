package com.bitfinex.core.strategies.levels;

import com.bitfinex.core.strategies.StrategyAlgorithm;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelPriceStrategy implements StrategyAlgorithm
{
    private static final Logger logger = LoggerFactory.getLogger(LevelPriceStrategy.class);
    IRestService iRestService;
    String symbol;
    CandleInterval strategyInterval;


    public LevelPriceStrategy(IRestService iRestService, String symbol, CandleInterval strategyInterval)
    {
        this.iRestService = iRestService;
        this.symbol = symbol;
        this.strategyInterval = strategyInterval;
    }

    @Override
    public void stop()
    {

    }

    @Override
    public double getAccountBalanceValue()
    {
        return 0;
    }

    @Override
    public void run()
    {

    }
}
