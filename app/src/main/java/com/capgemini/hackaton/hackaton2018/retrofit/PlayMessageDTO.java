package com.capgemini.hackaton.hackaton2018.retrofit;

/**
 * Created by MARBEL on 16/02/2018.
 */

public class PlayMessageDTO {
    private Integer receiverId;
    private String beaconId;

    public PlayMessageDTO(Integer receiverId, String beaconId) {
        this.receiverId = receiverId;
        this.beaconId = beaconId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public String getBeaconId() {
        return beaconId;
    }


}
