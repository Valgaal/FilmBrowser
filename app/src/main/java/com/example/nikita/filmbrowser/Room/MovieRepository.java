package com.example.nikita.filmbrowser.Room;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.nikita.filmbrowser.Models.GetDetailsMovieModel;
import com.example.nikita.filmbrowser.Models.SearchResultModel;
import com.example.nikita.filmbrowser.Network.MoviesAPI;
import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.Network.NetworkRequestWork;

import java.util.ArrayList;
import java.util.List;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
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

    public MovieDao dao;
    private MovieDetailsDao detailsDao;
    private Application application;
    public MoviesAPI api;
    private FavDao favDao;

    private static MovieRepository INSTANCE;

    private MovieRepository(final Application application1){
        application = application1;
        MoviewRoomDatabase db = MoviewRoomDatabase.getInstance(application);
        dao = db.filmDao();
        favDao = db.favDao();
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
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType
                (NetworkType.CONNECTED).build();
        OneTimeWorkRequest trendingRequest = new OneTimeWorkRequest.Builder(NetworkRequestWork.class)
                .setConstraints(constraints).build();
        WorkManager.getInstance().enqueue(trendingRequest);
        SharedPreferences sp = application.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(WORK_REQUEST_ID, trendingRequest.getId().toString());
        editor.commit();
    }

    public Observable<SearchModel> searchByApi(String query){
        return api.getSearchResult(API_KEY, query);
    }
    public Observable<List<SearchResultModel>> getTrendingDay(){
        return dao.getTrending()
                .flattenAsObservable(movies -> movies)
                .map(item-> convertToSearchModel(item))
                .toList()
                .toObservable();

    }

    public Observable<List<SearchResultModel>> getFavorites(){
        return dao.getTrending()
                .flattenAsObservable(movies -> movies)
                .map(item-> convertToSearchModel(item))
                .toList()
                .toObservable();

    }

    public SearchResultModel convertToSearchModel(Movie movie){
        SearchResultModel mModel = new SearchResultModel();
        mModel.setTitle(movie.getTitle());
        mModel.setReleaseDate(movie.getReleaseDate());
        mModel.setVoteAverage(movie.getRatingAvg());
        mModel.setPosterPath(movie.getPosterPath());
        mModel.setId(movie.getId());
        return mModel;
    }

    public void insertData(List<SearchResultModel> searchList){
    }

    public boolean wmJob() {

        SearchModel searchModel = api.getTrendingDay(API_KEY).blockingSingle();
        List<SearchResultModel> searchList = searchModel.getResults();
        for (int i = 0; i < searchList.size(); i++) {
            SearchResultModel resultModel = searchList.get(i);
            Movie movie = new Movie();
            if (resultModel.getTitle() == null) {
                movie.setTitle(resultModel.getName());
            } else {
                movie.setTitle(resultModel.getTitle());
            }
            movie.setId(resultModel.getId());
            movie.setPosterPath(resultModel.getPosterPath());
            movie.setRatingAvg(resultModel.getVoteAverage());
            movie.setReleaseDate(resultModel.convertReleaseDate());
            movie.setTrending(true);
            dao.getMovieById(movie.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Movie>() {
                        @Override
                        public void onSuccess(Movie mov) {
                            movie.setFavorites(mov.isFavorites());
                            updateMovie(movie);
                        }

                        @Override
                        public void onError(Throwable e) {
                            insertMovie(movie);
                        }
                    });

        }
        return true;
    }

    public Single<MovieDetails> getMovie(int id){
//        return Single.concat(detailsDao.getMovie(id), getMovieFromNetwork(id))
//                .firstElement()
//                .toSingle();
        return getMovieFromNetwork(id);
    }

    public Single<MovieDetails> getMovieFromNetwork(int id){
        return api.getMovie(id, API_KEY)
                .map(item-> convertToMovieDetails(item));
    }

    public MovieDetails convertToMovieDetails(GetDetailsMovieModel model){
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setReleaseDate(model.getReleaseDate());
        movieDetails.setId(model.getId());
        movieDetails.setOverview(model.getOverview());
        movieDetails.setPosterPath(model.getPosterPath());
        movieDetails.setRatingAvg(model.getVoteAverage());
        movieDetails.setRevenue(model.getRevenue());
        movieDetails.setTitle(model.getTitle());
        movieDetails.setRuntime(model.getRuntime());
        model.setStatus(model.getStatus());

        ArrayList<String> genreNames = new ArrayList<>();
        for(int i = 0; i < model.getGenres().size(); i++){
            genreNames.add(model.getGenres().get(i).getName());
        }

        ArrayList<String> countryNames = new ArrayList<>();
        for(int i = 0; i < model.getProductionCountries().size(); i++){
            countryNames.add(model.getProductionCountries().get(i).getName());
        }
        movieDetails.setCountries(countryNames);
        movieDetails.setGenres(genreNames);

        return movieDetails;
    }

    public Single<List<FavModel>> getFavMovies(){
        return favDao.getFavMovies();
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

}
