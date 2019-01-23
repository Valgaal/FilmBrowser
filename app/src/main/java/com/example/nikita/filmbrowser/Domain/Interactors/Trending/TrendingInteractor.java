package com.example.nikita.filmbrowser.Domain.Interactors.Trending;

import com.example.nikita.filmbrowser.Domain.Interactors.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.UI.MovieListModel;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;


public class TrendingInteractor {
    @Inject
    public IMovieRepository movieRepository;

    private InitWMUseCase initWMUseCase;
    private GetTrendingDayUseCase getTrendingDayUseCase;

    public TrendingInteractor() {
        App.getComponent().inject(this);
        initWMUseCase = new InitWMUseCase(movieRepository);
        getTrendingDayUseCase = new GetTrendingDayUseCase(movieRepository);

    }

    public Observable<List<MovieListModel>> getTrendingDaily() {
        return getTrendingDayUseCase.getTrendingDay()
                .doOnError(throwable -> {
                    startRequestFromDailyTrending();
                })
                .map(Converters::convertListToMovieListModel);
    }

    public void startRequestFromDailyTrending() {
        initWMUseCase.enqueueWM();
    }

    public void createWMRequest() {
        initWMUseCase.createWMRequest();
    }

    public UUID getWMId() {
        return initWMUseCase.getWMId();
    }

    public void updateMovie(MovieListModel movie) {
        new UpdateMovieDetailsUseCase(movieRepository).updateMovie(movie);
    }

    public void wmJob() {
        new WMJobUseCase(movieRepository).wmJob();
    }
}
