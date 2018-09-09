package com.example.nikita.filmbrowser.Navigation;

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

import com.example.nikita.filmbrowser.MovieViewModel;
import com.example.nikita.filmbrowser.MoviesAdapter;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Room.FavModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FragmentFavorites extends Fragment implements MoviesAdapter.OnViewClicked{
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

        mMovieViewModel.getFavMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<FavModel>() {
                    @Override
                    public void onSuccess(FavModel favModel) {
                        //mAdapter.setFilms(favModel.getMovies());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return view;
    }

    @Override
    public void filmSelected(int id) {

    }

    @Override
    public void addedToFav(int id) {

    }
}
