package com.example.nikita.filmbrowser.UI.Details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Room.MovieDetails;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.example.nikita.filmbrowser.UI.App;

import io.reactivex.Observable;

public class DetailsViewModel extends AndroidViewModel {
    private MovieRepository mRepository;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public Observable<MovieDetails> getMovie(int id){
        return mRepository.getMovie(id);
    }

    public void insertMovieDetails(MovieDetails movieDetails){
        mRepository.insertMovieDetails(movieDetails);
    }
}
