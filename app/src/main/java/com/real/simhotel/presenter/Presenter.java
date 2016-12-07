package com.real.simhotel.presenter;

/**
 * Created by liudan on 2016/12/7.
 */
public interface Presenter {
    void resume();

    void pause();

    void destroy();

    void requestData(Object... o);
}
