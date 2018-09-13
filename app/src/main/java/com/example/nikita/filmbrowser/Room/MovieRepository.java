package com.example.nikita.filmbrowser.Room;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.nikita.filmbrowser.Models.SearchResultModel;
import com.example.nikita.filmbrowser.Network.MoviesAPI;
import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.Network.NetworkRequestWork;

import java.util.List;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    public static final String BASE_SEARCH_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = "655780709d6f3360d269a64bd96c99d6";
    public final static String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    public final static String MY_PREF = "my_pref";
    public final static String WORK_REQUEST_ID = "work_id";

    private MovieDao dao;
    private MovieDetailsDao detailsDao;
    private Application application;
    private MoviesAPI api;

    private static MovieRepository INSTANCE;

    private MovieRepository(final Application application1){
        application = application1;
        MoviewRoomDatabase db = MoviewRoomDatabase.getInstance(application);
        dao = db.filmDao();
        detailsDao = db.detailsDao();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieRepository.BASE_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        api = retrofit.create(MoviesAPI.class);
    }

    public static MovieRepository getInstance(final Application application){
        if(INSTANCE == null){
            synchronized (MovieRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new MovieRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public void getTrendingDailyWM(){
        OneTimeWorkRequest trendingRequest = new OneTimeWorkRequest.Builder(NetworkRequestWork.class)
                .build();
        WorkManager.getInstance().enqueue(trendingRequest);
        SharedPreferences sp = application.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(WORK_REQUEST_ID, trendingRequest.getId().toString());
        editor.commit();
    }

    public Observable<List<Movie>> searchByApi(String query){
        return api.getSearchResult(API_KEY, query)
                .map(searchModel -> searchModel.getResults())
                .flatMap(searchResultModels ->
                            Observable.fromIterable(searchResultModels)
                            .map(item -> {
                                try {//эта проверка на случай того, если уже есть фильм, то тогда нужно узнать в избранном он или нет и обновить его
                                    Movie movie = dao.getMovieById(item.getId()).blockingGet();
                                    Movie converted = Converters.convertToMovie(item);
                                    converted.setFavorites(movie.isFavorites());
                                    return converted;
                                }catch (Exception e){
                                    return Converters.convertToMovie(item);
                                }
                            })
                )
                .toList()
                .toObservable() ;
    }
    public Observable<List<Movie>> getTrendingDay(){
        return dao.getTrending().toObservable();

    }

//    public Observable<List<SearchResultModel>> getTrendingDay(){
//        return dao.getTrending()
//                .flattenAsObservable(movies -> movies)
//                .map(item-> Converters.convertToSearchModel(item))
//                .toList()
//                .toObservable();
//
//    }

    public Observable<List<Movie>> getFavorites(){
        return dao.getFavorites().toObservable();

    }

    public void wmJob() {

        SearchModel searchModel = api.getTrendingDay(API_KEY).blockingSingle();
        List<SearchResultModel> searchList = searchModel.getResults();
        for (int i = 0; i < searchList.size(); i++) {
            SearchResultModel resultModel = searchList.get(i);
            Movie movie = Converters.convertToMovie(resultModel);
            movie.setTrending(true);
            try {
                Movie movieFromDb = dao.getMovieById(movie.getId()).blockingGet();
                movie.setFavorites(movieFromDb.isFavorites());
                updateMovie(movie);
            }catch (Exception e){
                insertMovie(movie);
            }
        }
    }

    public Observable<MovieDetails> getMovie(int id){
//        return Observable.concat(
//                detailsDao.getMovie(id).toObservable().onErrorResumeNext(throwable -> {
//                    return Observable.error(throwable);
//                }),
//                getMovieFromNetwork(id).toObservable().onErrorResumeNext(throwable -> {
//            return Observable.error(throwable);
//        })
//        );
        return detailsDao.getMovie(id).toObservable().onErrorResumeNext(throwable -> {//если нет в дб, то делает запрос
                    return getMovieFromNetwork(id).toObservable().onErrorResumeNext(networkThrowable -> {
                        return Observable.error(networkThrowable);
                    });
                });
    }

    private Single<MovieDetails> getMovieFromNetwork(int id){
        return api.getMovie(id, API_KEY)
                .map(item-> Converters.convertToMovieDetails(item));
    }

    public void insertMovie(Movie movie){
        Completable.fromAction(() -> dao.insert(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateMovie(Movie movie){
        Completable.fromAction(() -> dao.update(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void insertMovieDetails(MovieDetails movieDetails){
        Completable.fromAction(() -> detailsDao.insert(movieDetails))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
