package com.example.nikita.filmbrowser.Domain.Repositories;

import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Model.DB.MovieDetails;
import com.example.nikita.filmbrowser.Models.SearchModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IMovieRepository {

    void saveWMRequestId(String id);

    String getWMid();

    Observable<SearchModel> searchByApi(String query);

    Observable<SearchModel> getTrendingDaily();

    Single<MovieDetails> getMovieFromNetwork(int id);

    Single<List<Movie>> getTrendingDay();

    Single<List<Movie>> getFavorites();

    Single<Movie> getMovieById(int id);

    Single<MovieDetails> getMovie(int id);

    void insertMovie(Movie movie);

    void updateMovie(Movie movie);

    void insertMovieDetails(MovieDetails movieDetails);
}
