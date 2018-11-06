package com.example.nikita.filmbrowser.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nikita.filmbrowser.MoviesAdapter;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.UI.Details.DetailsActivity;
import com.example.nikita.filmbrowser.UI.Details.DetailsViewModel;
import com.example.nikita.filmbrowser.UI.Details.FragmentDetails;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseListFragment  extends Fragment implements MoviesAdapter.OnViewClicked{
    protected Disposable disposable;

    @Override
    public void onDestroyView() {
        if(disposable != null) {
            disposable.dispose();
        }
        super.onDestroyView();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void filmSelected(int id) {
        DetailsViewModel detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        disposable = detailsViewModel.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDetails -> {
                    detailsViewModel.insertMovieDetails(movieDetails);
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(FragmentDetails.MOVIE_DETAILS, movieDetails);
                    startActivity(intent);
                }, throwable -> {
                    if(throwable instanceof UnknownHostException){
                        Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
    }

    @Override
    public void addedToFav(Movie movie) {

    }

    @Override
    public void deleteFromFav(Movie movie) {

    }
}
