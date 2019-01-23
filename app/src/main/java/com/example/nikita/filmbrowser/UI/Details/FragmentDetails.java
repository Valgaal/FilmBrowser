package com.example.nikita.filmbrowser.UI.Details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikita.filmbrowser.R;
import com.squareup.picasso.Picasso;

public class FragmentDetails extends Fragment {

    public static final String MOVIE_DETAILS = "movie";

    public static FragmentDetails newInstance(MovieDetailsModel movieDetails) {
        FragmentDetails myFragment = new FragmentDetails();

        Bundle args = new Bundle();
        args.putSerializable(MOVIE_DETAILS, movieDetails);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView title = view.findViewById(R.id.title);
        TextView date = view.findViewById(R.id.date);
        TextView genres = view.findViewById(R.id.genres);
        TextView country = view.findViewById(R.id.country);
        TextView runtime = view.findViewById(R.id.runtime);
        TextView revenue = view.findViewById(R.id.revenue);
        TextView popularity = view.findViewById(R.id.popularity);
        TextView overview = view.findViewById(R.id.overview);
        ImageView image = view.findViewById(R.id.posterBig);

        Bundle bundle = this.getArguments();
        MovieDetailsModel movieDetails = (MovieDetailsModel) bundle.getSerializable(MOVIE_DETAILS);

        title.setText(movieDetails.getTitle());
        date.setText(movieDetails.getReleaseDate());
        genres.setText(movieDetails.getGenres());
        country.setText(movieDetails.getCountries());
        runtime.setText(movieDetails.getRuntime());
        if (movieDetails.getRevenue() == null) {
            revenue.setVisibility(View.GONE);
            view.findViewById(R.id.revenueTextView).setVisibility(View.GONE);
        } else {
            revenue.setText(movieDetails.getRevenue());
        }
        popularity.setText(movieDetails.getPopularity());
        overview.setText(movieDetails.getOverview());
        if (movieDetails.getPosterPath() != null) {
            Picasso.get()
                    .load(movieDetails.getPosterPath())
                    .resize(400, 400)
                    .centerInside()
                    .into(image);
        }

        return view;
    }
}
