package com.alehin.alpha.data;

import com.alehin.alpha.exceptions.NotFoundException;

import java.util.Map;

public class CurrencyRate {
    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private Map<String, Double> rates;

    public CurrencyRate() {

    }

    public CurrencyRate(String disclaimer, String license, Integer timestamp, String base, Map<String, Double> rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public Double getCurrencyRate(String code){
        if (rates.containsKey(code)){
            return rates.get(code);
        } else {
            throw new NotFoundException();
        }
    }
}