package com.bitfinex.core;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlgorithmService
{
    private List<Algorithm> algorithms;


    public AlgorithmService()
    {
        algorithms= new ArrayList<>();
        algorithms.add(new Algorithm("Algorithm-Level-1"));
        algorithms.add(new Algorithm("Algorithm-Level-2"));
        algorithms.add(new Algorithm("Algorithm-Level-3"));
    }

    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }
}
