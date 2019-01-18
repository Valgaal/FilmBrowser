package com.example.nikita.filmbrowser.Domain;

import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Model.DB.MovieDetails;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IMovieRepository {

    void getTrendingDailyWM();

    Observable<List<Movie>> searchByApi(String query);

    Single<List<Movie>> getTrendingDay();

    Single<List<Movie>> getFavorites();

    void wmJob();

    Observable<MovieDetails> getMovie(int id);

    void insertMovie(Movie movie);

    void updateMovie(Movie movie);

    void insertMovieDetails(MovieDetails movieDetails);
}
