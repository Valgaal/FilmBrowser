package com.example.nikita.filmbrowser.Room;

import android.app.Application;

import com.example.nikita.filmbrowser.MoviesAPI;
import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.NetworkRequestWork;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private static final String BASE_SEARCH_URL = "https://api.themoviedb.org/3/";
    public final static String API_KEY = "655780709d6f3360d269a64bd96c99d6";
    public final static String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";

    private MovieDao dao;
    private Application application;
    private MoviesAPI api;

    public MovieRepository(final Application application1){
        application = application1;
        MoviewRoomDatabase db = MoviewRoomDatabase.getInstance(application);
        dao = db.filmDao();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        api = retrofit.create(MoviesAPI.class);
    }

    public void getTrendingDailyWM(){
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType
                (NetworkType.CONNECTED).build();
        OneTimeWorkRequest trendingRequest = new OneTimeWorkRequest.Builder(NetworkRequestWork.class)
                .addTag(NetworkRequestWork.TAG)
                .setConstraints(constraints).build();
        WorkManager.getInstance().enqueue(trendingRequest);
    }

    public Observable<SearchModel> searchByApi(String query){
        return api.getSearchResult(API_KEY, query);


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
