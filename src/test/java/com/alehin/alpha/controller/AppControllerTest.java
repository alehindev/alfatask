package com.alehin.alpha.controller;

import com.alehin.alpha.exceptions.BadLabel;
import com.alehin.alpha.exceptions.NotFoundException;
import com.alehin.alpha.services.GifService;
import com.alehin.alpha.services.RatesService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppControllerTest {
    //services for work

    @MockBean
    private RatesService ratesService;
    @MockBean
    private GifService gifService;



    @Value("${gif.errorLabel}")
    private String errorLabel;
    @Value("${gif.richLabel}")
    private String richLabel;
    @Value("${gif.brokeLabel}")
    private String brokeLabel;
    @Value("${gif.defaultLabel}")
    private String defaultLabel;

    @Test
    public void getGifByCodeTest1(){
        Mockito.when(ratesService.getFlagByLabel(anyString())).thenThrow(NotFoundException.class);
    }
    @Test
    public void getGifByCodeTest2(){

        Mockito.when(ratesService.getFlagByLabel("RUB")).thenReturn((byte)0);

    }
    @Test
    public void getGifByCodeTest3(){
        String str = "";
        Mockito.when(gifService.getUriByLabel(richLabel)).thenReturn(str);
    }
    @Test
    public void getGifByCodeTest4(){
        String str = "";
        Mockito.when(gifService.getUriByLabel(brokeLabel)).thenReturn(str);
    }
    @Test
    public void getGifByCodeTest5(){
        Mockito.when(gifService.getUriByLabel(anyString())).thenThrow(BadLabel.class);
    }



}