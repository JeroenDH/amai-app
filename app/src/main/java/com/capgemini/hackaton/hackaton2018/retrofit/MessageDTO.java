package com.capgemini.hackaton.hackaton2018.retrofit;

/**
 * Created by sergdudk on 16/02/2018.
 */

public class MessageDTO {
    private Integer receiverId;
    private String senderName;

    //Base64 encoded sound
    private String data;

    //Beacon id
    private String beaconId;

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public MessageDTO(Integer receiverId, String senderName, String data, String beaconId) {
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.data = data;
        this.beaconId = beaconId;
    }
}
