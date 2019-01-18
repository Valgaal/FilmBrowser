package com.example.nikita.filmbrowser.UI.Search;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikita.filmbrowser.UI.MoviesAdapter;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.BaseListFragment;
import com.example.nikita.filmbrowser.Utils.Utils;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

public class FragmentSearch extends BaseListFragment {

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
        mAdapter = new MoviesAdapter(getActivity(), this);
        rw.setAdapter(mAdapter);

        searchButton.setOnClickListener(view1 -> {
            Utils.hideKeyboardFrom(getActivity(), editText);
            disposable = mViewModel.searchFilm(editText.getText().toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<Movie>>() {
                        @Override
                        public void onNext(List<Movie> movies) {
                            if(movies.size()!=0) {
                                mAdapter.setFilms(movies);
                            }else{
                                Toast.makeText(getActivity(),R.string.not_found, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if(e instanceof UnknownHostException){
                                Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
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
    public void addedToFav(Movie movie) {
        mViewModel.insertMovie(movie);
    }

    @Override
    public void deleteFromFav(Movie movie) {
        mViewModel.updateMovie(movie);
    }
}
