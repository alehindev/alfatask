package com.alehin.alpha.services;

import com.alehin.alpha.clients.FeignRatesClient;
import com.alehin.alpha.data.CurrencyRate;
import com.alehin.alpha.exceptions.NotFoundException;
import com.alehin.alpha.exceptions.SameTimeRatesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


@Service
public class RatesService {

    //rates
    private CurrencyRate yesterRates;
    private CurrencyRate todayRates;

    //client
    private FeignRatesClient RatesClient;
    @Value("${rates.api_key}")
    private String api_key;
    @Value("${rates.default-code}")
    private String defCode;
    //date-format
    @Value("${calendar.dateFormat}")
    private String dateFormat;


    @Autowired
    public RatesService(FeignRatesClient ratesClient) {
        RatesClient = ratesClient;
    }

    public CurrencyRate getYesterRates() {
        updateYesterRates();
        return yesterRates;
    }

    public CurrencyRate getTodayRates() {
        updateTodayRates();
        return todayRates;
    }

    /*
    * 0 - то курс повысился и кидаем rich
    * 1 - то курс понизился и кидаем broke
    * */
    public byte getFlagByLabel(String code){
        code = code.toUpperCase(Locale.ROOT);
        updateYesterRates();
        updateTodayRates();
        if(yesterRates.getTimestamp() == todayRates.getTimestamp()){
            throw new SameTimeRatesException();
        }
        if(yesterRates.getRates().get(code).isNaN()){
            throw new NotFoundException();
        }

        if(yesterRates.getCurrencyRate(code)-todayRates.getCurrencyRate(code) > Double.MIN_VALUE){
            return 1;
        }
        return 0;
    }

    private void updateTodayRates(){
        todayRates = RatesClient.getLatestRates(api_key);
        return ;
    }
    private void updateYesterRates(){
        Calendar yesterDate = Calendar.getInstance();
        yesterDate.add(Calendar.DATE, -1);
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        yesterRates = RatesClient.getHistoricalRates(df.format(yesterDate.getTime()), api_key);
        return ;
    }


}
