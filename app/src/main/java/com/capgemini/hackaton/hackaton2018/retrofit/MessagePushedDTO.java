package com.capgemini.hackaton.hackaton2018.retrofit;

/**
 * Created by sergdudk on 15/02/2018.
 */

public class MessagePushedDTO {
    private boolean success;

    private String result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
