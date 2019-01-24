package com.example.nikita.filmbrowser.Domain.Interactors.Trending;

import android.arch.lifecycle.LiveData;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.Network.NetworkRequestWork;

import java.util.UUID;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

class InitWMUseCase {

    private IMovieRepository movieRepository;
    private WorkManager workManager;

    InitWMUseCase(IMovieRepository movieRepository) {

        this.movieRepository = movieRepository;
        workManager = WorkManager.getInstance();
    }

    void enqueueWM() {
        OneTimeWorkRequest trendingRequest = new OneTimeWorkRequest.Builder(NetworkRequestWork.class)
                .build();
        workManager.enqueue(trendingRequest);
        movieRepository.saveWMRequestId(trendingRequest.getId().toString());
    }

    LiveData<WorkInfo> getWorkInfo() {
        UUID uuid = UUID.fromString(movieRepository.getWMid());
        return workManager.getWorkInfoByIdLiveData(uuid);
    }
}
