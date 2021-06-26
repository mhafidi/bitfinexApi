package draft;

import com.bitfinex.services.BitfinexWsClient;
import org.junit.Test;

import java.util.Stack;

public class TestBitfinexConnection
{

    @Test
    public void testCandleStick() throws InterruptedException
    {
        BitfinexWsClient bitfinexWsClient = new BitfinexWsClient();

    }

    @Test
    public void draftTest()
    {
        double value=0.0;
        double gama=1.1,zeta=1.1;

        if((gama-zeta)==0.0)
            System.out.println("toto");
    }
}
