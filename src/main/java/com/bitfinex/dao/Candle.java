package com.bitfinex.dao;

import org.json.JSONArray;
import org.json.JSONTokener;

public class Candle
{
    private long timestamp;
    private double open;
    private double close;
    private double high;
    private double low;
    private double volume;


    public Candle(String payload) throws DAOException {
        JSONArray jsonArray = new JSONArray(new JSONTokener(payload));
        if(jsonArray.length()!=6)
            throw new DAOException("unable to decode payload");
        timestamp =jsonArray.getInt(0);
        open = Double.parseDouble(jsonArray.get(1).toString());
        close = Double.parseDouble(jsonArray.get(2).toString());
        high = Double.parseDouble(jsonArray.get(3).toString());
        low = Double.parseDouble(jsonArray.get(4).toString());
        volume = Double.parseDouble(jsonArray.get(5).toString());


    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getVolume() {
        return volume;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Candle{" +
                "timestamp=" + timestamp +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                '}';
    }
}
