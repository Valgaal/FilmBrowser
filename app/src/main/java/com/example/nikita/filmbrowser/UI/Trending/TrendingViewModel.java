package com.example.nikita.filmbrowser.UI.Trending;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.Room.MovieDetails;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TrendingViewModel extends AndroidViewModel {
    @Inject
    MovieRepository mRepository;

    public TrendingViewModel(@NonNull Application application) {
        super(application);
        App.getComponent().inject(this);
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

