package com.bitfinex.core.strategies.levels;

import com.bitfinex.core.strategies.ABsStrategyAlgorithm;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//level price strategy + price contraction (volumes)
//
public class LevelPriceStrategy extends ABsStrategyAlgorithm
{
    private static final Logger logger = LoggerFactory.getLogger(LevelPriceStrategy.class);



    public LevelPriceStrategy(IRestService iRestService, String symbol, CandleInterval strategyInterval)
    {
        super(iRestService,symbol,strategyInterval);
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
