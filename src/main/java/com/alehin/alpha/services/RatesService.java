package com.alehin.alpha.services;

import com.alehin.alpha.clients.FeignRatesClient;
import com.alehin.alpha.data.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class RatesService {

    //rates
    private CurrencyRate yesterRates;
    private CurrencyRate todayRates;

    //client
    private FeignRatesClient RatesClient;
    @Value("${rates.api_key}")
    private String api_key;
    @Value("${rates.check.base}")
    private String base;



    @Autowired
    public RatesService(FeignRatesClient ratesClient) {
        RatesClient = ratesClient;
    }

    /*
    * 0 - то курс повысился и кидаем rich
    * 1 - то курс понизился и кидаем broke
    * 2 - ошибка
    *
    *
    * */
    public byte getFlagByLabel(String code){
        updateTodayRates();
        return (byte)((byte) Integer.parseInt(code) % 2 == 0 ? 0 : 4);
    }

    private void updateTodayRates(){
        todayRates = RatesClient.getHistoricalRates("2021-10-22", api_key);
        System.out.println(todayRates);
    }


}
