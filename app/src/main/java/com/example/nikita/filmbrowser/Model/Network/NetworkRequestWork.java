package com.example.nikita.filmbrowser.Model.Network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactors.Trending.TrendingInteractor;
import com.example.nikita.filmbrowser.Utils.Utils;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NetworkRequestWork extends Worker {

    public NetworkRequestWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (Utils.isOnline()) {
            new TrendingInteractor().wmJob();
            return Result.success();
        } else {
            return Result.failure();
        }
    }
}
