package com.bitfinex.core.strategies;

import java.util.TimerTask;

public interface StrategyAlgorithm extends Runnable
{
    void stop();
    double getAccountBalanceValue();
}
