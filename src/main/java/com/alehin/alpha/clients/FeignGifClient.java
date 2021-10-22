package com.alehin.alpha.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "FGifClient", url = "${gif.api.url}")
public interface FeignGifClient {

    @GetMapping("/random")
    ResponseEntity<Map> getRandomGif(@RequestParam("api_key") String api_key,@RequestParam("tag") String tag);
}
