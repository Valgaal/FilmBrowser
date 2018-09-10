package com.example.nikita.filmbrowser.UI.Details;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentDetails  extends Fragment{

    private Disposable disposable;
    private DetailsViewModel mViewModel;

    public static final String ID = "id";

    public static FragmentDetails newInstance(int id) {
        FragmentDetails myFragment = new FragmentDetails();

        Bundle args = new Bundle();
        args.putInt(ID, id);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
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
        int id = bundle.getInt(ID);

        disposable = mViewModel.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDetails -> {
                    mViewModel.insertMovieDetails(movieDetails);
                    title.setText(movieDetails.getTitle());
                    date.setText(movieDetails.getReleaseDate());
                    String genreString = "";
                    for(int i = 0; i< movieDetails.getGenres().size(); i++){
                        if(i + 1 == movieDetails.getGenres().size()){
                            genreString = genreString.concat(movieDetails.getGenres().get(i));
                        }
                        else{
                            genreString = genreString.concat(movieDetails.getGenres().get(i).concat("\n"));
                        }
                    }
                    genres.setText(genreString);

                    String countryString = "";
                    for(int i = 0; i< movieDetails.getCountries().size(); i++){
                        if(i + 1 == movieDetails.getCountries().size()){
                            countryString = countryString.concat(movieDetails.getCountries().get(i));
                        }
                        else {
                            countryString = countryString.concat(movieDetails.getCountries().get(i).concat("\n"));
                        }
                    }

                    country.setText(countryString);
                    runtime.setText(Integer.toString(movieDetails.getRuntime()));
                    revenue.setText(Integer.toString(movieDetails.getRevenue()));
                    popularity.setText(Double.toString(movieDetails.getPopularity()));
                    Picasso.get()
                            .load(MovieRepository.IMAGE_PATH.concat(movieDetails.getPosterPath()))
                            .resize(400,400)
                            .centerInside()
                            .into(image);
                    overview.setText(movieDetails.getOverview());
                }, throwable -> Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show());

        return view;
    }

    @Override
    public void onDestroyView() {
        if(disposable != null) {
            disposable.dispose();
        }
        super.onDestroyView();
    }
}
