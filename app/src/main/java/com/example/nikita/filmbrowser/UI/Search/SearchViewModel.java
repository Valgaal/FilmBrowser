package com.example.nikita.filmbrowser.UI.Search;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.nikita.filmbrowser.Domain.Interactor.InsertMovieUseCase;
import com.example.nikita.filmbrowser.Domain.Interactor.SearchMovieUseCase;
import com.example.nikita.filmbrowser.Domain.Interactor.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Utils.Utils;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

public class SearchViewModel extends AndroidViewModel {

    MutableLiveData<SearchViewState> stateLiveData = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertMovie(Movie movie){
        new InsertMovieUseCase().insertMovie(movie);
    }

    public void updateMovie(Movie movie){
        new UpdateMovieDetailsUseCase().updateMovie(movie);
    }

    public void searchTriggered(String searchText){
        stateLiveData.setValue(SearchViewState.start());
        new SearchMovieUseCase().searchByApi(searchText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> movies) {
                        if(movies.size()!=0) {
                            stateLiveData.setValue(SearchViewState.success(movies));
                        }else{
                            stateLiveData.setValue(SearchViewState.error(getApplication().getResources().getString(R.string.not_found)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof UnknownHostException){
                            stateLiveData.setValue(SearchViewState.error(getApplication().getResources().getString(R.string.internet_error)));
                        }else {
                            stateLiveData.setValue(SearchViewState.error(e.getMessage()));
                        }
                    }

                    @Override
                    public void onComplete() {
                        //убрать
                    }
                });
    }

}
