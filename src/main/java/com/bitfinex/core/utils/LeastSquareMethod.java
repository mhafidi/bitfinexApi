package com.bitfinex.core.utils;

import java.util.Map;
/* Line of Best Fit (Least Square Method):
*
* Now calculate xi−X , yi−Y , (xi−X)(yi−Y) , and (xi−X)^2 for each i . where X and Y are respectively the averages of xi and yi
*
* a = sum((xi−X)(yi−Y))/sum((xi-X))
*
* b= Y-aX
*
* the linear equation is f(x)=ax+b
* */
public class LeastSquareMethod
{
    double a;
    double b;

    Map<Double,Double> timePrice;


    public LeastSquareMethod(Map<Double, Double> timePrice)
    {
        this.timePrice = timePrice;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }
}
