package com.example.nikita.filmbrowser.Model.Network;

import com.example.nikita.filmbrowser.Models.GetDetailsMovieModel;
import com.example.nikita.filmbrowser.Models.SearchModel;


import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {


    @GET("search/multi")
    Observable<SearchModel> getSearchResult(
            @Query("api_key") String api_key,
            @Query("query") String query);

    @GET("trending/all/day")
    Observable<SearchModel> getTrendingDay(@Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Single<GetDetailsMovieModel> getMovie(@Path("movie_id") int id, @Query("api_key") String api_key);
}
