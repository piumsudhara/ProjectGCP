package com.example.pium.chef.RetrofitClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetCartItems
{
    private static final String BASE_URL = "http://10.10.86.246:8080/ChefService/rest/cart/";

    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitApiClient()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
