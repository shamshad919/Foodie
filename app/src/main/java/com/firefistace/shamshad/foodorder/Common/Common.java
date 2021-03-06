package com.firefistace.shamshad.foodorder.Common;

import com.firefistace.shamshad.foodorder.Remote.APIService;
import com.firefistace.shamshad.foodorder.Remote.RetrofitClient;

/**
 * Created by Zoro on 21-Apr-18.
 */

public class Common {
    private static final String BASE_URL="https://fcm.googleapis.com/";

    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
    public static APIService getFCMClient(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }



}
