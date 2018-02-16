package com.capgemini.hackaton.hackaton2018.retrofit;

/**
 * Created by sergdudk on 16/02/2018.
 */

public class MessageDTO {
    private Integer senderId;
    private String senderName;

    //Base64 encoded sound
    private String data;

    //Beacon id
    private String destinationId;

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
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

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public MessageDTO(Integer senderId, String senderName, String data, String destinationId) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.data = data;
        this.destinationId = destinationId;
    }
}
