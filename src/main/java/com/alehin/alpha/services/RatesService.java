package com.alehin.alpha.services;

import org.springframework.stereotype.Service;

@Service
public class RatesService {
    public RatesService(){

    }

    /*
    * 0 - то курс повысился и кидаем rich
    * 1 - то курс понизился и кидаем broke
    * 2 - ошибка
    *
    *
    * */
    public byte getFlagByLabel(String code){
        return (byte)((byte) Integer.parseInt(code) % 2 == 0 ? 0 : 4);
    }
}
