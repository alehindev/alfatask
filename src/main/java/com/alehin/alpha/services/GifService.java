package com.alehin.alpha.services;

import com.alehin.alpha.clients.FeignGifClient;
import com.alehin.alpha.clients.FeignRatesClient;
import com.alehin.alpha.exceptions.BadLabel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GifService {

    //LABELS
    @Value("${gif.richLabel}")
    private String rich;

    @Value("${gif.brokeLabel}")
    private String dummy;

    //client
    private FeignGifClient GifClient;
    @Value("${gif.api.key}")
    private String api_key;


    public GifService(FeignGifClient feignGifClient){
        this.GifClient = feignGifClient;
    }

    public String getUriByLabel(String label){
        String resURI;
        if(label.equalsIgnoreCase(dummy) || label.equalsIgnoreCase(rich)) {

            ResponseEntity<Map> response = GifClient.getRandomGif(api_key, label);
            if(response.getStatusCodeValue() != 200) {
                Object objVal = response.getBody().get("data");
                if (objVal instanceof LinkedHashMap){
                    resURI = ((LinkedHashMap<?, ?>) objVal).get("id").toString();
                    return resURI;
                }

            }
            throw new BadLabel();
        }
        throw new BadLabel();
    }
}
