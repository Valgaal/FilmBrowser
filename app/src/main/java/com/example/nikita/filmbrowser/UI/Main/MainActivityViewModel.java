package com.example.nikita.filmbrowser.UI.Main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactors.Details.DetailsInteractor;
import com.example.nikita.filmbrowser.R;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<DetailsViewState> stateLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private DetailsInteractor detailsInteractor;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        detailsInteractor = new DetailsInteractor();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    public void getMovie(int id) {
        disposable.add(detailsInteractor.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        convertedMovieDetails -> stateLiveData.setValue(DetailsViewState.success(convertedMovieDetails)),
                        throwable -> {
                            if (throwable instanceof UnknownHostException) {
                                stateLiveData.setValue(DetailsViewState.error(getApplication().getString(R.string.internet_error)));
                            } else {
                                stateLiveData.setValue(DetailsViewState.error(throwable.getMessage()));
                            }
                        }));
    }
}
