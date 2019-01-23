package com.example.nikita.filmbrowser.UI.Search;

import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.UI.MovieListModel;
import com.example.nikita.filmbrowser.UI.Status;

import java.util.List;

import io.reactivex.annotations.Nullable;

import static com.example.nikita.filmbrowser.UI.Status.*;

public class ListViewState {

    public final Status status;

    @Nullable
    public final List<MovieListModel> data;

    @Nullable
    public final String error;

    private ListViewState(Status status, @Nullable List<MovieListModel> data, @Nullable String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ListViewState loading() {
        return new ListViewState(LOADING, null, null);
    }

    public static ListViewState success(@NonNull List<MovieListModel> data) {
        return new ListViewState(SUCCESS, data, null);
    }

    public static ListViewState error(@NonNull String error) {
        return new ListViewState(ERROR, null, error);
    }

}
