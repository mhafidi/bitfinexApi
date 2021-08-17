package com.bitfinex.core.strategies;

public interface StrategyAlgorithm extends Runnable
{
    void stop();
    double getAccountBalanceValue();
}
