package com.example.android.inter;

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}
