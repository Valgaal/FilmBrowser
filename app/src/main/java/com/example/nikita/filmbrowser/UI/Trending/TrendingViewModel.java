package com.example.nikita.filmbrowser.UI.Trending;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactor.GetTrendingDayUseCase;
import com.example.nikita.filmbrowser.Domain.Interactor.InitWMUseCase;
import com.example.nikita.filmbrowser.Domain.Interactor.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Model.Repositories.MovieRepository;
import com.example.nikita.filmbrowser.UI.App;
import com.example.nikita.filmbrowser.UI.Search.SearchViewState;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TrendingViewModel extends AndroidViewModel {

    MutableLiveData<SearchViewState> stateLiveData = new MutableLiveData<>();

    public TrendingViewModel(@NonNull Application application) {
        super(application);
    }

    public void getTrendingDay() {
        new GetTrendingDayUseCase().getTrendingDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> searchModel) {
                        stateLiveData.setValue(SearchViewState.success(searchModel));
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

    public void startRequestFromDailyTrending() {
        new InitWMUseCase().initWMUseCase();
    }

    public UUID getWMId() {
        return new InitWMUseCase().getWMId();
    }

    public void updateMovie(Movie movie) {
        new UpdateMovieDetailsUseCase().updateMovie(movie);
    }

}

