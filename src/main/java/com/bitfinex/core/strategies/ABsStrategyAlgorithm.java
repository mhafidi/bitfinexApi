package com.bitfinex.core.strategies;

import com.bitfinex.core.strategies.sma.MovingAverageStrategy;
import com.bitfinex.core.utils.SizedStack;
import com.bitfinex.dao.Candle;
import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.IntStream;

public abstract class ABsStrategyAlgorithm implements StrategyAlgorithm
{
    private static final Logger logger = LoggerFactory.getLogger(ABsStrategyAlgorithm.class);
    protected SizedStack<Candle> candlesStack = new SizedStack<>(120); // TODO think of modifying the size tunable
    protected IRestService iRestService;
    protected CandleInterval strategyInterval;
    protected String symbol;
    protected long delay  = 0L;
    protected long second = 1000L;
    protected long minute = second*60;
    protected long hour = minute*60;
    protected double sma30 =0.0, sma120 =0.0; //state machine

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

    protected void updateSMAs()
    {
        sma120 = candlesStack.stream().mapToDouble(Candle::getClose).sum()/120;
        sma30 = IntStream.range(0,30).mapToDouble(i->candlesStack.get(i).getClose()).sum()/30;
        logger.info("current sma30["+ sma30 +"]  current sma120["+ sma120 +"]" );
    }

}
