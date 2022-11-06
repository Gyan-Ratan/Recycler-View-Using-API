package com.example.recyclerviewapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("drug/category")
    Call<JavaSchemapojo> getdata();
}
