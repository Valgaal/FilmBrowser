package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.Network.NetworkRequestWork;

import java.util.UUID;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class InitWMUseCase extends BaseMoviesUseCase {

    public void initWMUseCase() {
        OneTimeWorkRequest trendingRequest = new OneTimeWorkRequest.Builder(NetworkRequestWork.class)
                .build();
        WorkManager.getInstance().enqueue(trendingRequest);
        movieRepository.saveWMRequestId(trendingRequest.getId().toString());
    }

    public UUID getWMId() {
        return UUID.fromString(movieRepository.getWMid());
    }
}
