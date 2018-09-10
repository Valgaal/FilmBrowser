package com.example.nikita.filmbrowser.UI.Trending;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.Room.MovieDetails;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;

import io.reactivex.Observable;

public class TrendingViewModel extends AndroidViewModel {
    private MovieRepository mRepository;

    public TrendingViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public Observable<List<Movie>> getTrendingDay(){
        return mRepository.getTrendingDay();
    }

    public void startRequestFromDailyTrending(){
        mRepository.getTrendingDailyWM();
    }

    public void updateMovie(Movie movie){
        mRepository.updateMovie(movie);
    }

}

