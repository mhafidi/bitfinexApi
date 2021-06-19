package com.bitfinex.api.controllers;


import com.bitfinex.api.core.Algorithm;
import com.bitfinex.api.core.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-control")
public class ApiController
{
    @Autowired
    AlgorithmService algorithmService;
    @GetMapping("/algos")
    public List<Algorithm> getAlgos()
    {
        return algorithmService.getAlgorithms();
    }
}
