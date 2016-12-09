package com.real.simhotel.data;


import com.real.simhotel.config.Constants;

public class Response<T> {

    public String code;
    public String text;
    public T ext;


    public boolean isSuccess() {
        return code.equals(Constants.OK);
    }
}
