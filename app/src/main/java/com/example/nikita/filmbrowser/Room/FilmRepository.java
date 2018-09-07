package com.example.nikita.filmbrowser.Room;

import android.app.Application;

import com.example.nikita.filmbrowser.FilmsAPI;
import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.NetworkRequestWork;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;

public class FilmRepository {

    private static final String BASE_SEARCH_URL = "http://api.myservice.com/";

    private FilmDao dao;
    private Application application;

    public FilmRepository(final Application application1){
        application = application1;
        FilmRoomDatabase db = FilmRoomDatabase.getInstance(application);
        dao = db.filmDao();
    }

    public void getTrendingDailyWM(){
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType
                (NetworkType.CONNECTED).build();
        OneTimeWorkRequest trendingRequest = new OneTimeWorkRequest.Builder(NetworkRequestWork.class)
                .addTag(NetworkRequestWork.TAG)
                .setConstraints(constraints).build();
        WorkManager.getInstance().enqueue(trendingRequest);
    }

    public void createRetrofitClient(){

        //add As Singleton
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(rx.schedulers.Schedulers.io()))
                .build();


//        Observable<User> call = apiService.getUser(username);
//        Disposable disposable = call
//                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
//                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new Disposable<User>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // cast to retrofit.HttpException to get the response code
//                        if (e instanceof HttpException) {
//                            HttpException response = (HttpException)e;
//                            int code = response.code();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(User user) {
//                    }
//                });




    }

}
