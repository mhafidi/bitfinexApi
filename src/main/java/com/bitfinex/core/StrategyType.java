package com.bitfinex.core;

public enum StrategyType
{
    moving_avg("moving_avg"),
    level_price("level_price"),
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
