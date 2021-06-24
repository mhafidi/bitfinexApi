package com.bitfinex.api;


import com.bitfinex.core.MainControllerService;
import com.bitfinex.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/main-control")
public class MainController
{
    private static final Logger mainControllerLogger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    MainControllerService mainControllerService;

    @GetMapping("/startBot/symbols/{symbol}/algos/{algoType}")
    public String startBot(@PathVariable(value="symbol")String symbol,@PathVariable(value="algoType") String algoType) throws DAOException
    {
        mainControllerLogger.info("algoType="+algoType+" symbol="+symbol);
        return mainControllerService.startStrategy(algoType,symbol);
    }

}
