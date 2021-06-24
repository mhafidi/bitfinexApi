package com.bitfinex.dao;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Candles
{
    List<Candle> candles;

    public Candles(String payload) throws DAOException
    {
        JSONArray jsonArray = new JSONArray(new JSONTokener(payload));

        if(jsonArray.length()==0)
            throw new DAOException("unable to decode payload");
        candles=IntStream.range(0,jsonArray.length()).mapToObj(i-> {
            try
            {
                return new Candle((JSONArray) jsonArray.get(i));
            } catch (DAOException e)
            {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public List<Candle> getCandles()
    {
        return candles;
    }
}
