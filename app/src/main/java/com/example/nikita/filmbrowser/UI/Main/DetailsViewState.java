package com.example.nikita.filmbrowser.UI.Main;

import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.UI.Details.MovieDetailsModel;
import com.example.nikita.filmbrowser.UI.Status;

import io.reactivex.annotations.Nullable;

import static com.example.nikita.filmbrowser.UI.Status.*;

public class DetailsViewState {

    public final Status status;

    @Nullable
    public final MovieDetailsModel data;

    @Nullable
    public final String error;

    private DetailsViewState(Status status, @Nullable MovieDetailsModel data, @Nullable String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static DetailsViewState loading() {
        return new DetailsViewState(LOADING, null, null);
    }

    public static DetailsViewState success(@NonNull MovieDetailsModel data) {
        return new DetailsViewState(SUCCESS, data, null);
    }

    public static DetailsViewState error(@NonNull String error) {
        return new DetailsViewState(ERROR, null, error);
    }

}
