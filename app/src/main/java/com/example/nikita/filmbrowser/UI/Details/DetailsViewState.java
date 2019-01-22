package com.example.nikita.filmbrowser.UI.Details;

import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.UI.Status;

import io.reactivex.annotations.Nullable;

import static com.example.nikita.filmbrowser.UI.Status.*;

public class DetailsViewState {

    public final Status status;

    @Nullable
    public final ConvertedMovieDetails data;

    @Nullable
    public final String error;

    private DetailsViewState(Status status, @Nullable ConvertedMovieDetails data, @Nullable String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static DetailsViewState start() {
        return new DetailsViewState(START, null, null);
    }

    public static DetailsViewState loading() {
        return new DetailsViewState(LOADING, null, null);
    }

    public static DetailsViewState success(@NonNull ConvertedMovieDetails data) {
        return new DetailsViewState(SUCCESS, data, null);
    }

    public static DetailsViewState error(@NonNull String error) {
        return new DetailsViewState(ERROR, null, error);
    }

}
