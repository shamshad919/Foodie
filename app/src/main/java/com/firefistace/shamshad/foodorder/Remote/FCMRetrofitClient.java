package com.firefistace.shamshad.foodorder.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zoro on 22-Apr-18.
 */

public class FCMRetrofitClient {
    private static Retrofit retrofit=null;
    public static Retrofit getClient(String baseURL)
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
