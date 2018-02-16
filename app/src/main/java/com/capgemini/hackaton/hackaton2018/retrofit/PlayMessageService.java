package com.capgemini.hackaton.hackaton2018.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by MARBEL on 15/02/2018.
 */

public interface PlayMessageService {
    @POST("messaging/play")
    Call<PlayMessageResponseDTO> open(@Body PlayMessageDTO playMessageDTO);
}
