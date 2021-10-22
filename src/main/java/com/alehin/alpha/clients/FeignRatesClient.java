package com.alehin.alpha.clients;

import com.alehin.alpha.data.CurrencyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FRatesClient", url = "${rates.api.url}")
public interface FeignRatesClient {
    @GetMapping("/latest.json")
    CurrencyRate getLatestRates( @RequestParam("app_id") String api_key );
    @GetMapping("/historical/{date}.json")
    CurrencyRate getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String api_key);
}
