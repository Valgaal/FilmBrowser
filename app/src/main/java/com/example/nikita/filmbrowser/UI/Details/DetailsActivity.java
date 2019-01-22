package com.example.nikita.filmbrowser.UI.Details;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Model.DB.MovieDetails;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getResources().getString(R.string.movie_details));

        ConvertedMovieDetails movieDetails = ((ConvertedMovieDetails) getIntent().getExtras().getSerializable(FragmentDetails.MOVIE_DETAILS));

        FragmentDetails fragment = FragmentDetails.newInstance(movieDetails);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
