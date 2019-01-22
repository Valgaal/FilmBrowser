package com.example.nikita.filmbrowser.Domain.Interactors.Trending;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.Network.NetworkRequestWork;

import java.util.UUID;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

class InitWMUseCase{

    private IMovieRepository movieRepository;

    InitWMUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    void initWMUseCase() {
        OneTimeWorkRequest trendingRequest = new OneTimeWorkRequest.Builder(NetworkRequestWork.class)
                .build();
        WorkManager.getInstance().enqueue(trendingRequest);
        movieRepository.saveWMRequestId(trendingRequest.getId().toString());
    }

    UUID getWMId() {
        return UUID.fromString(movieRepository.getWMid());
    }
}
