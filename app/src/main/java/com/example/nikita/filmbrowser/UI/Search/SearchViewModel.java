package com.example.nikita.filmbrowser.UI.Search;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactors.Search.SearchInteractor;
import com.example.nikita.filmbrowser.UI.MovieListModel;
import com.example.nikita.filmbrowser.R;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

class SearchViewModel extends AndroidViewModel {

    MutableLiveData<ListViewState> stateLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private SearchInteractor searchInteractor;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchInteractor = new SearchInteractor();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    void updateMovie(MovieListModel movie) {
        searchInteractor.updateMovie(movie);
    }

    void searchTriggered(String searchText) {
        disposable.add(searchInteractor.searchMovie(searchText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> {
                            if (movies.size() != 0) {
                                stateLiveData.setValue(ListViewState.success(movies));
                            } else {
                                stateLiveData.setValue(ListViewState.error(getApplication().getResources().getString(R.string.not_found)));
                            }
                        },
                        throwable -> {
                            if (throwable instanceof UnknownHostException) {
                                stateLiveData.setValue(ListViewState.error(getApplication().getResources().getString(R.string.internet_error)));
                            } else {
                                stateLiveData.setValue(ListViewState.error(throwable.getMessage()));
                            }
                        }
                ));
    }

}
