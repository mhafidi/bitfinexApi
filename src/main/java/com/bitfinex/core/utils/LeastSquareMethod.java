package com.bitfinex.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* Line of Best Fit (Least Square Method):
*
* Now calculate xi−X , yi−Y , (xi−X)(yi−Y) , and (xi−X)^2 for each i . where X and Y are respectively the averages of xi and yi
* alpha=sum((xi−X)(yi−Y))
* beta=sum((xi-X))
* a = alpha/beta
*
* b= Y-aX
*
* the linear equation is f(x)=ax+b
* */
public class LeastSquareMethod
{
    double a;
    double b;
    private static final Logger logger = LoggerFactory.getLogger(LeastSquareMethod.class);
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

    private void calculateParameters()
    {
        double x_avg=0.0;
        double y_avg=0.0;
        double alpha=0.0,beta=0.0;
        List<Double> xi= new ArrayList<>(timePrice.keySet());
        List<Double> yi= new ArrayList<>(timePrice.values());
        for(int i=0;i<timePrice.size();i++)
        {
           x_avg=xi.get(i)+x_avg;
           y_avg=yi.get(i)+y_avg;
        }
        x_avg=x_avg/timePrice.size();
        y_avg=y_avg/timePrice.size();
        for(int i=0;i<timePrice.size();i++)
        {
            alpha=(xi.get(i)-x_avg)*(yi.get(i)-y_avg)+alpha;
            beta=(xi.get(i)-x_avg)+beta;
        }
        if(logger.isDebugEnabled())
            logger.debug("alpha["+alpha+"] beta["+beta+"]");

        a=alpha/beta;
        b= y_avg-a*x_avg;
        if(logger.isDebugEnabled())
            logger.debug(this.toString());
    }

    @Override
    public String toString() {
        return "LeastSquareMethod{"+a+"X"+b+'}';
    }
}
