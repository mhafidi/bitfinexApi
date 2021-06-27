# bitfinexApi

This application is an online strategy trading REST API tool which can be used to automatically trade in bitfinex crypto-currency exchange.
It is easy to use through a REST interface:


This trading draft unlike other code examples it contains real trading strategy that works pretty good with high risk-reward ratio:

#### How to use:

- checkout code
- compile it and run (maven based):

```
mvn clean compile
mvn spring-boot:start
```
- if the application fails to start because of an issue of a busy port, then change the value of the variable server.port in application.properties file and recompile and rerun the application 
- run a postman client and play with these endpoints:

```
http://localhost:8080/main-control/startBot/symbols/tETHUSD/algos/moving_avg
http://localhost:8080/main-control/stopBot
http://localhost:8080/main-control/getaccountbalance
```


#### Current featues:

- Rest Interface to start/stop the trading bot  with the choice of a valid trading strategy plus a valid trading currency pair




