package com.bitfinex.core.strategies;

import com.bitfinex.core.utils.SizedStack;
import com.bitfinex.dao.Candle;
import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;

import java.util.concurrent.ScheduledExecutorService;

public abstract class ABsStrategyAlgorithm implements StrategyAlgorithm
{
    protected SizedStack<Candle> candlesStack = new SizedStack<>(120); // TODO think of modifying the size tunable
    protected IRestService iRestService;
    protected CandleInterval strategyInterval;
    protected String symbol;
    protected long delay  = 0L;
    protected long second = 1000L;
    protected long minute = second*60;
    protected long hour = minute*60;

    protected ScheduledExecutorService executor;


    public ABsStrategyAlgorithm(IRestService iRestService, String symbol, CandleInterval strategyInterval) {
        this.iRestService = iRestService;
        this.strategyInterval = strategyInterval;
        this.symbol = symbol;
    }
    protected void updateCandleStack() throws DAOException {
        String payload=iRestService.getCandle(strategyInterval.toString(),symbol);
        Candle candle = new Candle(payload);
        candlesStack.push(candle);
    }
}
