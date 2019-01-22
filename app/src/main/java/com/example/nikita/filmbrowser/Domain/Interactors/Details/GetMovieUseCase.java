package com.example.nikita.filmbrowser.Domain.Interactors.Details;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.MovieDetails;
import com.example.nikita.filmbrowser.UI.Details.ConvertedMovieDetails;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

class GetMovieUseCase{

    private IMovieRepository movieRepository;

    GetMovieUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Observable<ConvertedMovieDetails> getMovie(int id) {
        return movieRepository.getMovie(id).onErrorResumeNext(throwable -> {//если нет в дб, то делает запрос
            return movieRepository.getMovieFromNetwork(id)
                    .map(item -> {
                        movieRepository.insertMovieDetails(item);
                        return item;
                    }).onErrorResumeNext(Single::error);
        }).map(this::convertMovieDetails).toObservable();
    }

    private ConvertedMovieDetails convertMovieDetails(MovieDetails movieDetails) {
        ConvertedMovieDetails convertedMovieDetails = new ConvertedMovieDetails();
        convertedMovieDetails.setId(movieDetails.getId());
        convertedMovieDetails.setTitle(movieDetails.getTitle());
        convertedMovieDetails.setOverview(movieDetails.getOverview());
        convertedMovieDetails.setPopularity(Double.toString(movieDetails.getPopularity()));
        convertedMovieDetails.setPosterPath(movieDetails.getPosterPath());
        convertedMovieDetails.setRatingAvg(Double.toString(movieDetails.getRatingAvg()));
        convertedMovieDetails.setCountries(getStringFromList(movieDetails.getCountries()));
        convertedMovieDetails.setGenres(getStringFromList(movieDetails.getGenres()));
        convertedMovieDetails.setRevenue(movieDetails.getRevenue() + " $");
        return convertedMovieDetails;
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
