package com.bitfinex.api;


import com.bitfinex.core.MainControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main-control")
public class MainController
{
    private final MainControllerService mainControllerService;
    @Autowired

    public MainController(MainControllerService mainControllerService) {
        this.mainControllerService = mainControllerService;
    }
    @GetMapping("/startBot")
    public int startBot()
    {
        return mainControllerService.startStrategy();
    }

}
