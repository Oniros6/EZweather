package com.ezweather.app.util;

/**
 * Created by Oniros on 2016/4/7.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
