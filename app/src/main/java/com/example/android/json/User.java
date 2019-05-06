package com.example.android.json;

public class User {

    private UserData userInfo;
    private int errorCode;
    private String errorMsg;

    public UserData getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserData userInfo) {
        this.userInfo = userInfo;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
