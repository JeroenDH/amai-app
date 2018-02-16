package com.capgemini.hackaton.hackaton2018.retrofit;

/**
 * Created by sergdudk on 16/02/2018.
 */

public class MessageDTO {
    private Integer senderid;
    private String senderName;

    //Base64 encoded sound
    private String data;

    //Beacon id
    private String destinationId;

    public Integer getSenderid() {
        return senderid;
    }

    public void setSenderid(Integer senderid) {
        this.senderid = senderid;
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

    public MessageDTO(Integer senderid, String senderName, String data, String destinationId) {
        this.senderid = senderid;
        this.senderName = senderName;
        this.data = data;
        this.destinationId = destinationId;
    }
}
