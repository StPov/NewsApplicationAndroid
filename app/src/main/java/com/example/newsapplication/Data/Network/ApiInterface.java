package com.example.newsapplication.Data.Network;

import com.example.newsapplication.Data.Models.News;
import com.example.newsapplication.Data.Models.SearchedSources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<News> getNews(@Query("q") String searchText, @Query("apiKey") String apiKey);

    @GET("sources")
    Call<SearchedSources> getSources(@Query("apiKey") String apiKey);

}
