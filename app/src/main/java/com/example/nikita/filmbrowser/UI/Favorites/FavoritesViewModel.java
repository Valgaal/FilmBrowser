package com.example.nikita.filmbrowser.UI.Favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactor.GetFavoritesUseCase;
import com.example.nikita.filmbrowser.Domain.Interactor.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.Search.SearchViewState;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FavoritesViewModel extends AndroidViewModel {

    MutableLiveData<SearchViewState> stateLiveData = new MutableLiveData<>();

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
    }

    public void getFavorites() {
        new GetFavoritesUseCase().getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        stateLiveData.setValue(SearchViewState.success(movies));
                    }

                    @Override
                    public void onError(Throwable e) {
                        stateLiveData.setValue(SearchViewState.error(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateMovie(Movie movie) {
        new UpdateMovieDetailsUseCase().updateMovie(movie);
    }

}
