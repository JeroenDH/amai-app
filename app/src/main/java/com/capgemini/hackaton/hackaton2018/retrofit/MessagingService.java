package com.capgemini.hackaton.hackaton2018.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sergdudk on 16/02/2018.
 */

public interface MessagingService {
    @POST("messaging")
    Call<MessagePushedDTO> push(@Body MessageDTO messageDTO);
}
