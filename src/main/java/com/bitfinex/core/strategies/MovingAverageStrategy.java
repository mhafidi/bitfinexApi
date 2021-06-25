package com.bitfinex.core.strategies;

import com.bitfinex.dao.Candle;
import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MovingAverageStrategy implements StrategyAlgorithm
{
    private static final Logger logger = LoggerFactory.getLogger(MovingAverageStrategy.class);
    IRestService iRestService;
    String symbol;
    ScheduledExecutorService executor;
    long delay  = 0L;
    long period = 60*1000L;

    public MovingAverageStrategy(IRestService iRestService, String symbol)
    {
        this.iRestService = iRestService;
        this.symbol = symbol;
    }

    @Override
    public void run()
    {
        TimerTask repeatedTask = new TimerTask()
        {
            public void run()
            {
                processStrategy();
            }
        };

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
    }

    private void processStrategy()
    {
        try
        {
            String payload=iRestService.getCandle(CandleInterval.MINUTE_5.toString(),symbol);
            Candle candle = new Candle(payload);

            logger.info(candle.toString());

        }
        catch (DAOException e)
        {
            logger.error(e.toString());
        }
    }

    private void initialization()
    {

    }

    @Override
    public void stop()
    {
        if(executor!=null)
        {
            logger.warn("Other conditions will be added to avoid stopping the strategy with opened positions");
            executor.shutdown();
        }
    }
}
