package com.example.nikita.filmbrowser.UI.Details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactor.GetMovieUseCase;
import com.example.nikita.filmbrowser.Domain.Interactor.InsertMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Model.DB.MovieDetails;
import com.example.nikita.filmbrowser.Model.Repositories.MovieRepository;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.UI.App;
import com.example.nikita.filmbrowser.UI.Search.SearchViewState;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsViewModel extends AndroidViewModel {

    public MutableLiveData<DetailsViewState> stateLiveData = new MutableLiveData<>();

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMovie(int id) {
        new GetMovieUseCase().getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ConvertedMovieDetails>() {

                    @Override
                    public void onNext(ConvertedMovieDetails movieDetails) {
                        stateLiveData.setValue(DetailsViewState.success(movieDetails));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            stateLiveData.setValue(DetailsViewState.error(getApplication().getString(R.string.internet_error)));
                        } else {
                            stateLiveData.setValue(DetailsViewState.error(e.getMessage()));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void insertMovieDetails(MovieDetails movieDetails) {
        new InsertMovieDetailsUseCase().insertMovieDetails(movieDetails);
    }
}
