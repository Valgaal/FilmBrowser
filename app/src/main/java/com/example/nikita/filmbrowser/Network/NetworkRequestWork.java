package com.example.nikita.filmbrowser.Network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nikita.filmbrowser.App;
import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.Models.SearchResultModel;
import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.Room.MovieDao;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.example.nikita.filmbrowser.Room.MoviewRoomDatabase;

import java.util.List;


import androidx.work.Worker;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NetworkRequestWork extends Worker {

    private Disposable disposable;

    @NonNull
    @Override
    public Result doWork() {
        MovieRepository repository = ((App) getApplicationContext()).getRepository();

        if(repository.wmJob()){
            return Result.SUCCESS;
        }else{
            return  Result.FAILURE;
        }
    }

    @Override
    public void onStopped(boolean cancelled) {
        super.onStopped(cancelled);
        if(disposable != null) {
            disposable.dispose();
        }
    }
}
