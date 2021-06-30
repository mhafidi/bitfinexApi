package com.bitfinex.core.strategies.sma;

import com.bitfinex.core.strategies.StrategyAlgorithm;
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
    double current_diff_long_entry_position=0.0,previous_diff_long_entry_position=0.0;
    private boolean managePositionPhase=false;

    private double accountBalanceValue =1000.0; // starting with 1000$
    final double FEES_RATE=0.02/100;
    private double openPositionPrice;
    private double amountPosition=0.05;
    private boolean longPosition;
    private boolean shortPosition=false;

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
            Candle lastCandle;
            updateCandleStack();
            updateSMAs();
            lastCandle = candlesStack.get(0);
            marketTrendChange=marketTrendUpdate();
            if(managePositionPhase)
            {
                managePosition(lastCandle);
                return;
            }
            if(smaCrossConfiguration)
            {
                smaConfigurationCrossPhase(lastCandle);
                return;
            }
            if(marketTrendChange)
            {
                logger.info("Market Trend change");
                smaCrossConfiguration = true;

            }

        }
        catch (DAOException e)
        {
            logger.error(e.toString());
        }
    }

    private void managePosition(Candle lastCandle) {
        double positionPlusFees;
        double positionValue;
        if(longPosition)
        {
            if(maMarketTrend==MAMarketTrend.downtrend)
            {
                //TODO the sell value will be the highest bid in the orderbook
                logger.warn("Close Long position, Cause: [trend changed] current price ["+ lastCandle.getLow()+"]");
                positionValue = lastCandle.getLow()*amountPosition;
                positionPlusFees = positionValue - positionValue*FEES_RATE;
                accountBalanceValue = accountBalanceValue +positionPlusFees;
                managePositionPhase = false;
                longPosition = false;
                return ;
            }
            if(lastCandle.getClose()>=sma30)
            {
                //Time to take profit and return to wait to a good market opportunity
                logger.info("Timing Take Profit");
                if(current_diff_long_entry_position==0.0)
                {
                    current_diff_long_entry_position = sma30 -sma120;
                    logger.info("Skip calculating previous SMA diff");
                    return ;
                }
                else
                {
                    previous_diff_long_entry_position = current_diff_long_entry_position;
                    current_diff_long_entry_position = sma30 -sma120;
                    if(current_diff_long_entry_position<previous_diff_long_entry_position)
                    {
                        if(lastCandle.getClose()<sma30)
                        {
                            logger.warn("Close Long position, Cause: [T-P: configuration] current price ["+ lastCandle.getLow()+"]");
                            positionValue = lastCandle.getLow()*amountPosition;
                            positionPlusFees = positionValue - positionValue*FEES_RATE;
                            accountBalanceValue = accountBalanceValue +positionPlusFees;
                            managePositionPhase = false;
                            longPosition = false;
                            return ;
                        }
                    }
                    return ;
                }


            }
            if(lastCandle.getClose()<sma120)
            {
                double lossRate= (openPositionPrice- lastCandle.getClose())/openPositionPrice;
                logger.info("check stop loss");
                if(lossRate>=0.01)
                {
                    positionValue = lastCandle.getLow()*amountPosition;
                    positionPlusFees = positionValue - positionValue*FEES_RATE;
                    accountBalanceValue = accountBalanceValue +positionPlusFees;
                    managePositionPhase = false;
                    longPosition = false;
                    logger.warn("Close Long position, Cause: [Stop loss was hit]");
                }
                return ;
            }

        }
        if(shortPosition)
        {
            return ;
        }
        return ;
    }

    private void smaConfigurationCrossPhase(Candle lastCandle)
    {
        double positionValue;
        double positionPlusFees;
        if(maMarketTrend==MAMarketTrend.uptrend)
        {

            if(lastCandle.getClose()>= sma30)
            {
                logger.info("Timing entry long position");
                if(current_diff_long_entry_position==0.0)
                {
                    current_diff_long_entry_position = lastCandle.getClose() - sma120;
                    logger.info("Skip updating previous diff ");
                }
                else
                {

                    previous_diff_long_entry_position = current_diff_long_entry_position;
                    current_diff_long_entry_position = lastCandle.getClose() - sma120;
                    if(current_diff_long_entry_position>previous_diff_long_entry_position)
                    {

                        positionValue = candlesStack.get(0).getHigh() * 0.05;
                        positionPlusFees=positionValue+positionValue*FEES_RATE;
                        if(positionPlusFees>= accountBalanceValue)
                        {
                            logger.error("Insufficient balance, your current Balance is ["+accountBalanceValue+"]");
                            smaCrossConfiguration=false;
                            return;
                        }

                        else
                        {
                            managePositionPhase=true;
                            longPosition =true;
                            logger.info("Enter position - Market Position at the price [" + candlesStack.get(0).getHigh() + "] with the value of" +
                                    " [" + positionValue + "]");
                            openPositionPrice =  candlesStack.get(0).getHigh();
                            accountBalanceValue=accountBalanceValue-positionPlusFees;
                            previous_diff_long_entry_position=0.0;current_diff_long_entry_position=0.0;
                            smaCrossConfiguration=false;
                            return;
                        }

                    }
                }



            }

        }
        if(maMarketTrend==MAMarketTrend.downtrend)
        {
            smaCrossConfiguration = false;//TODO second part of the algorithm for short positions
            return;
        }
        return;
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

    public double getAccountBalanceValue()
    {
        return accountBalanceValue;
    }
}
