package com.example.nikita.filmbrowser.UI.Search;

import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Models.MovieListModel;
import com.example.nikita.filmbrowser.UI.Status;

import java.util.List;

import io.reactivex.annotations.Nullable;

import static com.example.nikita.filmbrowser.UI.Status.*;

public class SearchViewState {

    public final Status status;

    @Nullable
    public final List<MovieListModel> data;

    @Nullable
    public final String error;

    private SearchViewState(Status status, @Nullable List<MovieListModel> data, @Nullable String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static SearchViewState start() {
        return new SearchViewState(START, null, null);
    }

    public static SearchViewState loading() {
        return new SearchViewState(LOADING, null, null);
    }

    public static SearchViewState success(@NonNull List<MovieListModel> data) {
        return new SearchViewState(SUCCESS, data, null);
    }

    public static SearchViewState error(@NonNull String error) {
        return new SearchViewState(ERROR, null, error);
    }

}
