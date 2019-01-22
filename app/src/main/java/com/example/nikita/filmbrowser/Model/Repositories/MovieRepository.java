package com.example.nikita.filmbrowser.Model.Repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Model.DB.MovieDao;
import com.example.nikita.filmbrowser.Model.DB.MovieDetails;
import com.example.nikita.filmbrowser.Model.DB.MovieDetailsDao;
import com.example.nikita.filmbrowser.Model.DB.MoviewRoomDatabase;
import com.example.nikita.filmbrowser.Model.Network.MoviesAPI;
import com.example.nikita.filmbrowser.Models.SearchModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository implements IMovieRepository {

    private static final String BASE_SEARCH_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = "655780709d6f3360d269a64bd96c99d6";
    public final static String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private final static String MY_PREF = "my_pref";
    private final static String WORK_REQUEST_ID = "work_id";

    private MovieDao movieDao;
    private MovieDetailsDao detailsDao;
    private Application application;
    private MoviesAPI api;

    public MovieRepository(final Application application1) {
        application = application1;
        MoviewRoomDatabase db = MoviewRoomDatabase.getInstance(application);
        movieDao = db.filmDao();
        detailsDao = db.detailsDao();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieRepository.BASE_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        api = retrofit.create(MoviesAPI.class);
    }

    public void saveWMRequestId(String uiid) {
        SharedPreferences sp = application.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(WORK_REQUEST_ID, uiid);
        editor.apply();
    }

    public String getWMid() {
        SharedPreferences sp = application.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        return sp.getString(WORK_REQUEST_ID, "");
    }

    public Observable<SearchModel> searchByApi(String query) {
        return api.getSearchResult(API_KEY, query);
    }

    public Single<List<Movie>> getTrendingDay() {
        return movieDao.getTrending();

    }

    public Single<Movie> getMovieById(int id) {
        return movieDao.getMovieById(id);
    }

    public Single<List<Movie>> getFavorites() {
        return movieDao.getFavorites();

    }

    public Observable<SearchModel> getTrendingDaily() {
        return api.getTrendingDay(API_KEY);
    }

    public Single<MovieDetails> getMovie(int id) {
        return detailsDao.getMovie(id)
                .map(item -> {
                    item.setPosterPath(IMAGE_PATH + item.getPosterPath());
                    return item;
                });
    }

    public Single<MovieDetails> getMovieFromNetwork(int id) {
        return api.getMovie(id, API_KEY)
                .map(Converters::convertToMovieDetails);
    }

    public void insertMovie(Movie movie) {
        movieDao.insert(movie);
    }

    public void updateMovie(Movie movie) {
        movieDao.update(movie);
    }

    public void insertMovieDetails(MovieDetails movieDetails) {
        detailsDao.insert(movieDetails);
    }

}
