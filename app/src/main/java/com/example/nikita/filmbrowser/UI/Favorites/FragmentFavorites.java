package com.example.nikita.filmbrowser.UI.Favorites;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nikita.filmbrowser.Models.MovieListModel;
import com.example.nikita.filmbrowser.UI.MainActivity;
import com.example.nikita.filmbrowser.UI.MoviesAdapter;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.Search.SearchViewState;

public class FragmentFavorites extends Fragment implements MoviesAdapter.FavoritesChooser{
    private FavoritesViewModel mViewModel;
    private MoviesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        mViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        RecyclerView rw = view.findViewById(R.id.rw);
        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MoviesAdapter((MainActivity) getActivity(), this);
        rw.setAdapter(mAdapter);
        mViewModel.stateLiveData.observe(this, this::displayState);
        mViewModel.getFavorites();
        return view;
    }

    private void displayState(SearchViewState searchViewState) {
        switch (searchViewState.status) {
            case SUCCESS:
                mAdapter.setFilms(searchViewState.data);
                break;
            case ERROR:
                Toast.makeText(getActivity(), searchViewState.error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void addedToFav(MovieListModel movie) {
        mViewModel.updateMovie(movie);
        mAdapter.deleteMovie(movie);
    }

    @Override
    public void deleteFromFav(MovieListModel movie) {
        mViewModel.updateMovie(movie);
        mAdapter.notifyDataSetChanged();
    }

}
