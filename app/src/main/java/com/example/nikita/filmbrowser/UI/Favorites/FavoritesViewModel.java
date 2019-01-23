package com.example.nikita.filmbrowser.UI.Favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactors.Favorites.FavoritesInteractor;
import com.example.nikita.filmbrowser.UI.MovieListModel;
import com.example.nikita.filmbrowser.UI.Search.ListViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritesViewModel extends AndroidViewModel {

    MutableLiveData<ListViewState> stateLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FavoritesInteractor favoritesInteractor;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        favoritesInteractor = new FavoritesInteractor();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    void getFavorites() {
        disposable.add(favoritesInteractor.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> stateLiveData.setValue(ListViewState.success(movies)),
                        throwable -> stateLiveData.setValue(ListViewState.error(throwable.getMessage()))

                ));
    }

    public void updateMovie(MovieListModel movie) {
        new FavoritesInteractor().updateMovie(movie);
    }

}
