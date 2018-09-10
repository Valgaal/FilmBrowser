package com.example.nikita.filmbrowser.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nikita.filmbrowser.MoviesAdapter;
import com.example.nikita.filmbrowser.Room.Movie;

import io.reactivex.disposables.Disposable;

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
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(FragmentDetails.ID,id);
        startActivity(intent);
    }

    @Override
    public void addedToFav(Movie movie) {

    }

    @Override
    public void deleteFromFav(Movie movie) {

    }
}
