package com.example.nikita.filmbrowser.UI.Trending;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import androidx.work.State;
import androidx.work.WorkManager;

public class FragmentTrending extends Fragment implements MoviesAdapter.FavoritesChooser {

    private TrendingViewModel mViewModel;
    private MoviesAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        mSwipe = view.findViewById(R.id.swipe_refresh);
        mViewModel = ViewModelProviders.of(this).get(TrendingViewModel.class);
        RecyclerView rw = view.findViewById(R.id.rw_trending);
        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MoviesAdapter((MainActivity) getActivity(), this);
        rw.setAdapter(mAdapter);
        mViewModel.stateLiveData.observe(this, this::updateScreen);
        mViewModel.getTrendingDay();
        mViewModel.initWM();
        mSwipe.setOnRefreshListener(() -> {
            mViewModel.startRequestFromDailyTrending();
        });
        return view;
    }

    public void updateScreen(SearchViewState searchViewState) {
        switch (searchViewState.status) {
            case SUCCESS:
                mAdapter.setFilms(searchViewState.data);
                mSwipe.setRefreshing(false);
                break;
            case ERROR:
                Toast.makeText(getActivity(), searchViewState.error, Toast.LENGTH_LONG).show();
                mSwipe.setRefreshing(false);
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
