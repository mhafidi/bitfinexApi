package com.bitfinex.core.strategies;

import com.bitfinex.core.utils.SizedStack;
import com.bitfinex.dao.Candle;
import com.bitfinex.dao.Candles;
import com.bitfinex.dao.DAOException;
import com.bitfinex.services.bitfinex_rest_api.CandleInterval;
import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MovingAverageStrategy implements StrategyAlgorithm
{
    private static final Logger logger = LoggerFactory.getLogger(MovingAverageStrategy.class);
    IRestService iRestService;
    String symbol;
    ScheduledExecutorService executor;
    long delay  = 0L;
    long second = 1000L;
    long minute = second*60;
    long hour = minute*60;
    CandleInterval strategyInterval;
    SizedStack<Candle> candlesStack = new SizedStack<>(120); // TODO think of modifying the size tunable
    double sma30 =0.0, sma120 =0.0; //state machine
    MAMarketTrend maMarketTrend=MAMarketTrend.unset;
    boolean marketTrendChange =false;
    private boolean smaCrossConfiguration;

    public MovingAverageStrategy(IRestService iRestService, String symbol, CandleInterval strategyInterval)
    {
        this.iRestService = iRestService;
        this.symbol = symbol;
        this.strategyInterval =strategyInterval;
    }

    @Override
    public void run()
    {
        initialization();
        TimerTask repeatedTask = new TimerTask()
        {
            public void run()
            {
                processStrategy();
            }
        };

        executor = Executors.newSingleThreadScheduledExecutor();
        delay =getPeriod();
        executor.scheduleAtFixedRate(repeatedTask, delay, getPeriod(), TimeUnit.MILLISECONDS);
    }

    private long getPeriod()
    {
        switch (strategyInterval)
        {
            case MINUTE_1:
                return minute;
            case HOUR_1:
                return hour;
            case MINUTE_5:
                return minute * 5;
        }
        return second;
    }

    private void processStrategy()
    {
        try
        {

            updateCandleStack();
            updateSMAs();
            if(smaCrossConfiguration)
            {

                return;
            }
            marketTrendChange=marketTrendUpdate();
            if(marketTrendChange)
            {
                logger.info("Market Trend change");

            }




        }
        catch (DAOException e)
        {
            logger.error(e.toString());
        }
    }

    private void updateCandleStack() throws DAOException {
        String payload=iRestService.getCandle(strategyInterval.toString(),symbol);
        Candle candle = new Candle(payload);
        candlesStack.push(candle);
    }

    private void initialization()
    {
        logger.info("initialization of the candles stack");
        try
        {
            String payload=iRestService.getCandles(strategyInterval.toString(),symbol);
            Candles candles = new Candles(payload);

            for(int i=candles.getCandles().size()-1;i>=0;i--)
                candlesStack.push(candles.getCandles().get(i));
            logger.info("CandleStick Stack initialized time scale["+ strategyInterval +"]");
            updateSMAs();
            marketTrendUpdate();

        }
        catch (DAOException e)
        {
            logger.error(e.toString());
        }

    }

    private boolean marketTrendUpdate()
    {
        boolean marketTrendChanged;
        if(sma30 - sma120 <0.0)
        {
            marketTrendChanged= maMarketTrend == MAMarketTrend.uptrend;
            maMarketTrend = MAMarketTrend.downtrend;
        }
        else
        {
            marketTrendChanged= maMarketTrend == MAMarketTrend.downtrend;
            maMarketTrend = MAMarketTrend.uptrend;
        }
        return marketTrendChanged;
    }

    private void updateSMAs()
    {
        sma120 = candlesStack.stream().mapToDouble(Candle::getClose).sum()/120;
        sma30 = IntStream.range(0,30).mapToDouble(i->candlesStack.get(i).getClose()).sum()/30;
        logger.info("current sma30["+ sma30 +"]  current sma120["+ sma120 +"]" );
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
