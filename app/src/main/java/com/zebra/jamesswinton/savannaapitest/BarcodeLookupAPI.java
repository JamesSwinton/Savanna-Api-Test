package com.zebra.jamesswinton.savannaapitest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface BarcodeLookupAPI {

  @Headers({"apikey: IMZXNyRkKzHp3MnNNmxe3hwdUa7Xqjpq"})
  @GET("barcode/lookup/")
  Call<UpcProduct> barcodeLookup(
      @Query("upc") String barcode
  );

}
