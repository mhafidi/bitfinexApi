package draft;

import com.bitfinex.services.BitfinexWsClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

public class TestBitfinexConnection
{

    @Test
    public void testCandleStick() throws InterruptedException
    {
        BitfinexWsClient bitfinexWsClient = new BitfinexWsClient();

    }

    /**
     * 0  |1  |2  |3  |4  |5  |6  |7  |8  |9  |10  |11  |12
     * 6  |4  |5  |3  |7  |2  |3  |6  |4  |7  |3   |4   |6
     */

    @Test
    public void draftTest()
    {
        ArrayList<Double> prices= new ArrayList<>();

        prices.add(6.0);prices.add(4.0);prices.add(5.0);prices.add(3.0);prices.add(7.0);prices.add(2.0);prices.add(3.0);prices.add(6.0);
        prices.add(4.0);prices.add(7.0);prices.add(3.0);prices.add(4.0);prices.add(6.0);
        
    }
}
