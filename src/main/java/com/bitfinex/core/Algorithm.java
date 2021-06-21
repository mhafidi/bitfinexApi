package com.bitfinex.core;

public class Algorithm
{
    String name;

    public Algorithm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "name='" + name + '\'' +
                '}';
    }
}
