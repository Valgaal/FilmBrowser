package com.example.nikita.filmbrowser.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nikita.filmbrowser.Models.SearchResultModel;
import com.example.nikita.filmbrowser.MovieViewModel;
import com.example.nikita.filmbrowser.MoviesAdapter;
import com.example.nikita.filmbrowser.R;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FragmentFavorites extends BaseListFragment{
    private MovieViewModel mMovieViewModel;
    private MoviesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        RecyclerView rw = view.findViewById(R.id.rw);
        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MoviesAdapter(getActivity(), this);
        rw.setAdapter(mAdapter);

        disposable = mMovieViewModel.getTrendingDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<SearchResultModel>>() {

                    @Override
                    public void onNext(List<SearchResultModel> searchModel) {
                        mAdapter.setFilms(searchModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return view;
    }

    @Override
    public void filmSelected(int id) {
        super.filmSelected(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void addedToFav(SearchResultModel model) {
        super.addedToFav(model);
    }

    @Override
    public void deleteFromFav(SearchResultModel model) {
        super.deleteFromFav(model);
    }
}
