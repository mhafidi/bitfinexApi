package com.bitfinex.core;

public enum StrategyType
{
    moving_avg("moving_avg"),
    ADX_contraction("ADX_contraction");

    String strategyType;

    StrategyType(String strategyType)
    {
        this.strategyType = strategyType;
    }
    @Override
    public String toString()
    {
        return "StrategyType{" +
                "strategyType='" + strategyType + '\'' +
                '}';
    }
}
