package com.leferti.api.util;

import java.math.BigDecimal;

public final class StringUtil {

    private StringUtil(){

    }

    public static BigDecimal formatMoney(String money){
        BigDecimal formated = new BigDecimal(0);
        if(money!=null && !money.trim().equals("")){
            String format = money.substring(0, money.length()-3);
            format = format.replaceAll("R.", "");
            String cents = money.substring(money.length()-2);
            format = format.replace(",", "");
            format = format.replace(".","");
            formated = new BigDecimal(format.concat("." + cents));
        }
        return formated;
    }


}
