package com.example.nikita.filmbrowser.UI.Search;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikita.filmbrowser.Models.MovieListModel;
import com.example.nikita.filmbrowser.UI.MainActivity;
import com.example.nikita.filmbrowser.UI.MoviesAdapter;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Utils.Utils;

public class FragmentSearch extends Fragment implements MoviesAdapter.FavoritesChooser{

    private SearchViewModel mViewModel;
    private MoviesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        Button searchButton = view.findViewById(R.id.searchButton);
        EditText editText = view.findViewById(R.id.searchEditText);
        RecyclerView rw = view.findViewById(R.id.rw);
        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MoviesAdapter((MainActivity) getActivity(), this);
        rw.setAdapter(mAdapter);
        searchButton.setOnClickListener(view1 -> {
            Utils.hideKeyboardFrom(getActivity(), view1);
            mViewModel.searchTriggered(editText.getText().toString());
        });
        mViewModel.stateLiveData.observe(this, this::displayState);
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
    }

    @Override
    public void deleteFromFav(MovieListModel movie) {
        mViewModel.updateMovie(movie);
    }
}
