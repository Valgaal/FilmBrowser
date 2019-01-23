package com.example.nikita.filmbrowser.UI.Trending;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactors.Trending.TrendingInteractor;
import com.example.nikita.filmbrowser.UI.MovieListModel;
import com.example.nikita.filmbrowser.UI.Search.ListViewState;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TrendingViewModel extends AndroidViewModel {

    MutableLiveData<ListViewState> stateLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private TrendingInteractor trendingInteractor;

    public TrendingViewModel(@NonNull Application application) {
        super(application);
        trendingInteractor = new TrendingInteractor();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    void getTrendingDay() {
        disposable.add(trendingInteractor.getTrendingDaily()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> stateLiveData.setValue(ListViewState.success(movies)),
                        throwable -> {
                            stateLiveData.setValue(ListViewState.error(throwable.getMessage()));
                        }
                ));
    }

    void startRequestFromDailyTrending() {
        trendingInteractor.startRequestFromDailyTrending();
    }

    void initWM() {
        trendingInteractor.createWMRequest();
    }

    UUID getWMId() {
        return trendingInteractor.getWMId();
    }

    void updateMovie(MovieListModel movie) {
        trendingInteractor.updateMovie(movie);
    }

}

