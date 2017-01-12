package com.real.simhotel.model;

/**
 * Created by liudan on 2017/1/12.
 */
public class Quote {

    public String hotelName;
    public int prcie;

    public static Quote testQuote(String  name, int price){
        Quote result = new Quote();
        result.hotelName = name;
        result.prcie = price;

        return result;
    }
}
