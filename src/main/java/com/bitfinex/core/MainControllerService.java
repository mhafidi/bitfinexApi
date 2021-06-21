package com.bitfinex.core;

import com.bitfinex.services.bitfinex_rest_api.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainControllerService {

    private final IRestService iRestService;

    @Autowired
    public MainControllerService(IRestService iRestService) {
        this.iRestService = iRestService;
    }


    public int startStrategy()
    {
        return 0;
    }
}
