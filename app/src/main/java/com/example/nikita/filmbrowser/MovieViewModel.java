package com.example.nikita.filmbrowser;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.Models.SearchResultModel;
import com.example.nikita.filmbrowser.Room.MovieDetails;
import com.example.nikita.filmbrowser.Room.MovieRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public class MovieViewModel extends AndroidViewModel{
    private MovieRepository mRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public Observable<SearchModel> searchFilm(String query){
        return mRepository.searchByApi(query);
    }

    public Observable<List<SearchResultModel>> getTrendingDay(){
        return mRepository.getTrendingDay();
    }

    public Observable<List<SearchResultModel>> getFavorites(){
        return mRepository.getTrendingDay();
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

    public Single<List<FavModel>> getFavMovies(){
        return mRepository.getFavMovies();
    }
}
