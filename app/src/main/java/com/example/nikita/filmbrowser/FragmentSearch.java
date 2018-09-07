package com.example.nikita.filmbrowser;

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

import com.example.nikita.filmbrowser.Models.SearchModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class FragmentSearch extends Fragment implements MoviesAdapter.OnViewClicked {

    private MovieViewModel mMovieViewModel;
    private Disposable disposable;
    private MoviesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        Button searchButton = view.findViewById(R.id.searchButton);
        EditText editText = view.findViewById(R.id.searchEditText);
        RecyclerView rw = view.findViewById(R.id.rw);
        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MoviesAdapter(getActivity(), FragmentSearch.this);
        rw.setAdapter(mAdapter);

        searchButton.setOnClickListener(view1 ->

                disposable = mMovieViewModel.searchFilm(editText.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchModel>() {
                    @Override
                    public void onNext(SearchModel searchModel) {
                            if(searchModel.getResults().size()!=0) {
                                mAdapter.setFilms(searchModel.getResults());
                            }else{
                                Toast.makeText(getActivity(),R.string.not_found, Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                }
        ));


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void filmSelected(int id) {

    }

    @Override
    public void addedToFav(int id) {

    }
}
