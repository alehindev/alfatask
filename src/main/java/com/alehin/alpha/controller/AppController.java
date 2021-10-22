package com.alehin.alpha.controller;

import com.alehin.alpha.data.CurrencyRate;
import com.alehin.alpha.exceptions.NotFoundException;
import com.alehin.alpha.services.GifService;
import com.alehin.alpha.services.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class AppController {

    //services for work
    private RatesService ratesService;
    private GifService gifService;

    //gifs labels from .properties file
    @Value("${gif.errorLabel}")
    private String errorLabel;
    @Value("${gif.richLabel}")
    private String richLabel;
    @Value("${gif.brokeLabel}")
    private String brokeLabel;
    @Value("${gif.defaultLabel}")
    private String defaultLabel;

    /*
    @Value("${server.port}")
    private String serverPort;
    */

    //constructor

    @Autowired
    public AppController(RatesService ratesService, GifService gifService) {
        this.ratesService = ratesService;
        this.gifService = gifService;
    }

    //CHECK OF RATES SERVICE

    @GetMapping("today-rate")
    public CurrencyRate getTodayRate(){
        return ratesService.getTodayRates();
    }
    @GetMapping("yester-rate")
    public CurrencyRate getYesterRate(){
        return ratesService.getYesterRates();
    }

    //api get request
    @GetMapping("get-gif/{code}")
    public ResponseEntity getGifByCode(@PathVariable String code){
        ResponseEntity res = new ResponseEntity("Bad request",HttpStatus.BAD_REQUEST);
        String codeLabel = defaultLabel;
        String gifURI = "";
        byte codeFlag = ratesService.getFlagByLabel(code);
        switch (codeFlag){
            case 0:
                codeLabel = richLabel;
                break;
            case 1:
                codeLabel = brokeLabel;
                break;
            default:
                throw new NotFoundException();
        }
        gifURI = gifService.getUriByLabel(codeLabel);
        res = new ResponseEntity("<img src=\"https://media1.giphy.com/media/"+gifURI+"/giphy.gif\" />", HttpStatus.OK);
        return res;
    }

}
