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

import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.Details.DetailsActivity;
import com.example.nikita.filmbrowser.UI.Details.DetailsViewModel;
import com.example.nikita.filmbrowser.UI.Details.DetailsViewState;
import com.example.nikita.filmbrowser.UI.Details.FragmentDetails;

public class BaseListFragment extends Fragment implements MoviesAdapter.OnViewClicked {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void filmSelected(int id) {
        DetailsViewModel detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        detailsViewModel.getMovie(id);
        detailsViewModel.stateLiveData.observe(this, this::displayState);
    }

    private void displayState(DetailsViewState detailsViewState) {
        switch (detailsViewState.status) {
            case SUCCESS:
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(FragmentDetails.MOVIE_DETAILS, detailsViewState.data);
                startActivity(intent);
                break;
            case ERROR:
                Toast.makeText(getActivity(), detailsViewState.error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void addedToFav(Movie movie) {

    }

    @Override
    public void deleteFromFav(Movie movie) {

    }
}
