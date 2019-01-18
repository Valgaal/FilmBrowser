package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Domain.IMovieRepository;

import javax.inject.Inject;

abstract class BaseMoviesUseCase {
    @Inject
    IMovieRepository movieRepository;
}
