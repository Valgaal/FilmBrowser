package com.example.nikita.filmbrowser;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.Room.MovieRepository;

import io.reactivex.Observable;


public class MovieViewModel extends AndroidViewModel{
    private MovieRepository mRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
    }

    public Observable<SearchModel> searchFilm(String query){
        return mRepository.searchByApi(query);
    }
}
