package com.example.nikita.filmbrowser.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.nikita.filmbrowser.Room.MovieRepository;

import java.util.List;
import java.util.UUID;

import androidx.work.WorkManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FragmentTrending extends BaseListFragment{

    private MovieViewModel mMovieViewModel;
    private MoviesAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        mSwipe = view.findViewById(R.id.swipe_refresh);
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        RecyclerView rw = view.findViewById(R.id.rw_trending);
        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MoviesAdapter(getActivity(), this);
        rw.setAdapter(mAdapter);
        updateScreen();

        mSwipe.setOnRefreshListener(() -> {
            mMovieViewModel.startRequestFromDailyTrending();
            SharedPreferences sp = getActivity().getSharedPreferences(MovieRepository.MY_PREF, Context.MODE_PRIVATE);
            final String id = sp.getString(MovieRepository.WORK_REQUEST_ID,"");
            WorkManager.getInstance().getStatusById(UUID.fromString(id))
                    .observe(this, workStatus -> {
                        if(workStatus != null && workStatus.getState().isFinished()) {
                            updateScreen();
                        }
                    });
        });

        return view;
    }

    public void updateScreen(){
        disposable = mMovieViewModel.getTrendingDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<SearchResultModel>>() {

                    @Override
                    public void onNext(List<SearchResultModel> searchModel) {
                        mAdapter.setFilms(searchModel);
                        mSwipe.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        mSwipe.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
