package com.example.android.json;

/**
 * 实体基类
 */

public class BaseBean {
    /**
     * 服务器返回的错误码
     */
    private int errorCode;
    /**
     * 服务器返回的成功或失败的提示
     */
    private String errorMsg;
    /**
     * 服务器返回的数据
     */
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
