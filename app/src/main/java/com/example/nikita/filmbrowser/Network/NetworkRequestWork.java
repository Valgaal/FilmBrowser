package com.example.nikita.filmbrowser.Network;

import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.UI.App;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.example.nikita.filmbrowser.Utils.Utils;

import androidx.work.Worker;

public class NetworkRequestWork extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        MovieRepository repository = ((App) getApplicationContext()).getRepository();

        if(Utils.isOnline()){
            repository.wmJob();
            return Result.SUCCESS;
        }else{
            return  Result.FAILURE;
        }
    }
}
