package com.example.shamshad.foodorder.Remote;

import com.example.shamshad.foodorder.Model.MyResponse;
import com.example.shamshad.foodorder.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Zoro on 21-Apr-18.
 */

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAr-9xRQA:APA91bEQPPbZEPAryk3IoOJVsdaMMQVCp9k-tPqsFmtOMPx8PqhTbWrOvEwcUMQaCgs94-noqm7O6Fgt7J2fjQWbYcES7VtFMRhg1k3eVtwmSKKqvWQpJUH96DEAvUUmu5PSy149_woR"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
