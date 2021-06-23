package com.bitfinex.dao;

public class DAOException  extends Exception
{
    String message;

    public DAOException(String message)
    {
        super(message);
        this.message=message;
    }


    @Override
    public String toString() {
        return "DAOException{" +
                "message='" + message + '\'' +
                '}';
    }
}
