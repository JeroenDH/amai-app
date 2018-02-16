package com.capgemini.hackaton.hackaton2018.retrofit;

import java.util.List;

/**
 * Created by MARBEL on 16/02/2018.
 */

public class PlayMessageResponseDTO {
    private String succes;
    private List<PlayMessageDataDTO> messages;

    public String getSucces() {
        return succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }

    public List<PlayMessageDataDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<PlayMessageDataDTO> messages) {
        this.messages = messages;
    }
}
