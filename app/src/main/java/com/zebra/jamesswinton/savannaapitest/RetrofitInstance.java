package com.zebra.jamesswinton.savannaapitest;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

  private static Retrofit retrofitInstance = null;
  private static final String BASE_URL = "https://uat-api.zebra.com/v1/tools/";

  public static Retrofit getInstance() {
    if (retrofitInstance == null) {
      retrofitInstance = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
          .build();
    }
    return retrofitInstance;
  }

  private RetrofitInstance() { }
}