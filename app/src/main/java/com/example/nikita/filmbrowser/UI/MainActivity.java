package com.example.nikita.filmbrowser.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.UI.Details.DetailsActivity;
import com.example.nikita.filmbrowser.UI.Details.DetailsViewModel;
import com.example.nikita.filmbrowser.UI.Details.DetailsViewState;
import com.example.nikita.filmbrowser.UI.Details.FragmentDetails;
import com.example.nikita.filmbrowser.UI.Favorites.FragmentFavorites;
import com.example.nikita.filmbrowser.UI.Search.FragmentSearch;
import com.example.nikita.filmbrowser.UI.Trending.FragmentTrending;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.FilmSelector {

    private Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_trending:
                        selectItem(0);
                        toolbar.setTitle(getResources().getString(R.string.title_daily_trending));
                        return true;
                    case R.id.navigation_search:
                        selectItem(1);
                        toolbar.setTitle(getResources().getString(R.string.title_search));
                        return true;
                    case R.id.navigation_favorites:
                        selectItem(2);
                        toolbar.setTitle(getResources().getString(R.string.title_favorites));
                        return true;
                }
                return false;
            };

    public void selectItem(int position){

        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentTrending();
                break;
            case 1:
                fragment = new FragmentSearch();
                break;
            case 2:
                fragment = new FragmentFavorites();
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(false);//отключает app title
        if(savedInstanceState == null){
            selectItem(0);
        }
        toolbar.setTitle(getResources().getString(R.string.title_daily_trending));
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra(FragmentDetails.MOVIE_DETAILS, detailsViewState.data);
                startActivity(intent);
                break;
            case ERROR:
                Toast.makeText(this, detailsViewState.error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
