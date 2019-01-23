package com.example.nikita.filmbrowser.Domain.Interactors.Details;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.MovieDetails;
import com.example.nikita.filmbrowser.UI.Details.MovieDetailsModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

class GetMovieUseCase {

    private IMovieRepository movieRepository;

    GetMovieUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Observable<MovieDetailsModel> getMovie(int id) {
        return movieRepository.getMovie(id).onErrorResumeNext(throwable -> {//если нет в дб, то делает запрос
            return movieRepository.getMovieFromNetwork(id)
                    .map(item -> {
                        movieRepository.insertMovieDetails(item);
                        return item;
                    }).onErrorResumeNext(Single::error);
        }).map(this::convertMovieDetails).toObservable();
    }

    private MovieDetailsModel convertMovieDetails(MovieDetails movieDetails) {
        MovieDetailsModel movieDetailsModel = new MovieDetailsModel();
        movieDetailsModel.setId(movieDetails.getId());
        movieDetailsModel.setTitle(movieDetails.getTitle());
        movieDetailsModel.setOverview(movieDetails.getOverview());
        movieDetailsModel.setPopularity(Double.toString(movieDetails.getPopularity()));
        movieDetailsModel.setPosterPath(movieDetails.getPosterPath());
        movieDetailsModel.setRatingAvg(Double.toString(movieDetails.getRatingAvg()));
        movieDetailsModel.setCountries(getStringFromList(movieDetails.getCountries()));
        movieDetailsModel.setGenres(getStringFromList(movieDetails.getGenres()));
        movieDetailsModel.setRevenue(movieDetails.getRevenue() + " $");
        return movieDetailsModel;
    }

    private String getStringFromList(List<String> genres) {
        String genreString = "";
        for (int i = 0; i < genres.size(); i++) {
            if (i + 1 == genres.size()) {
                genreString = genreString.concat(genres.get(i));
            } else {
                genreString = genreString.concat(genres.get(i).concat("\n"));
            }
        }
        return genreString;
    }
}
