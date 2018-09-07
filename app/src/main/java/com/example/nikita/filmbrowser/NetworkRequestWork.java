package com.example.nikita.filmbrowser;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.work.Worker;

public class NetworkRequestWork extends Worker {

    public static final String TAG = "work";
    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: start");

        try {
            for (int i = 0; i < 1; i++) {
                TimeUnit.SECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doWork: end");

        return Result.SUCCESS;
    }
}
