package com.example.nikita.filmbrowser;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.Models.SearchResultModel;
import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.Room.MovieDetails;
import com.example.nikita.filmbrowser.Room.MovieRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MovieViewModel extends AndroidViewModel{
    private MovieRepository mRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public Observable<SearchModel> searchFilm(String query){
        return mRepository.searchByApi(query);
    }

    public Observable<List<Movie>> getTrendingDay(){
        return mRepository.getTrendingDay();
    }

    public Observable<List<Movie>> getFavorites(){
        return mRepository.getFavorites();
    }

    public void startRequestFromDailyTrending(){
        mRepository.getTrendingDailyWM();
    }

    public Single<MovieDetails> getMovie(int id){
        return mRepository.getMovie(id);
    }

    public Single<MovieDetails> getMovieFromNetwork(int id){
        return mRepository.getMovieFromNetwork(id);
    }

    public void insertMovie(Movie movie){
        mRepository.insertMovie(movie);
    }

    public void updateMovie(Movie movie){
        mRepository.updateMovie(movie);
    }
}
