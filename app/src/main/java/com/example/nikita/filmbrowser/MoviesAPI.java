package com.example.nikita.filmbrowser;

import com.example.nikita.filmbrowser.Models.SearchModel;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPI {


    @GET("search/multi")
    Observable<SearchModel> getSearchResult(
            @Query("api_key") String api_key,
            @Query("query") String query);

    @GET("trending/all/day")
    Observable<SearchModel> getTrendingDay(@Query("api_key") String api_key);
}
