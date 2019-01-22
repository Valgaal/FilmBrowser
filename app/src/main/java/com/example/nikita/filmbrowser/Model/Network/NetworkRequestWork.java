package com.example.nikita.filmbrowser.Model.Network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Interactor.WMJobUseCase;
import com.example.nikita.filmbrowser.UI.App;
import com.example.nikita.filmbrowser.Model.Repositories.MovieRepository;
import com.example.nikita.filmbrowser.Utils.Utils;

import javax.inject.Inject;

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
            new WMJobUseCase().wmJob();
            return Result.SUCCESS;
        } else {
            return Result.FAILURE;
        }
    }
}
