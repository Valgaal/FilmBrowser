package com.example.nikita.filmbrowser.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikita.filmbrowser.Models.MovieListModel;
import com.example.nikita.filmbrowser.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<MovieListModel> mMovies;
    private Context context;
    private FilmSelector filmSelector;
    private FavoritesChooser favoritesChooser;
    private final LayoutInflater mInflater;

    public interface FilmSelector{
        void filmSelected(int id);
    }
    public interface FavoritesChooser{
        void addedToFav(MovieListModel movie);
        void deleteFromFav(MovieListModel movie);
    }

    public MoviesAdapter(MainActivity mainActivity, FavoritesChooser favoritesChooser) {
        mInflater = LayoutInflater.from(mainActivity);
        this.filmSelector = mainActivity;
        this.favoritesChooser = favoritesChooser;
        this.context = mainActivity;
}

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.rw_item, viewGroup, false);
        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        MovieListModel movie = mMovies.get(i);
        moviesViewHolder.title.setText(movie.getTitle());
        moviesViewHolder.ratingAvg.setText(movie.getRatingAvg());
        if(movie.getPosterPath()!= null) {
            Picasso.get()
                    .load(movie.getPosterPath())
                    .resize(150,150)
                    .centerInside()
                    .into(moviesViewHolder.poster);
        }
        moviesViewHolder.itemView.setOnClickListener(view -> filmSelector.filmSelected(movie.getId()));
        moviesViewHolder.favButton.setOnClickListener(new FavClick(movie, moviesViewHolder.favButton));
        if(movie.isFavorites()){
            moviesViewHolder.favButton.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
        }else{
            moviesViewHolder.favButton.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_off));
        }
    }

    @Override
    public int getItemCount() {
        if (mMovies != null)
            return mMovies.size();
        else return 0;
    }

    public void setFilms(List<MovieListModel> films){
        mMovies = films;
        notifyDataSetChanged();
    }

    public void deleteMovie(MovieListModel movie){
        mMovies.remove(movie);
        notifyDataSetChanged();
    }


    class MoviesViewHolder extends RecyclerView.ViewHolder{

        private final ImageView poster;
        private final TextView title;
        private final TextView ratingAvg;
        private final ImageButton favButton;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.titleText);
            ratingAvg = itemView.findViewById(R.id.popularity);
            favButton = itemView.findViewById(R.id.favoritesButton);
        }
    }

    class FavClick implements View.OnClickListener{

        MovieListModel movie;
        ImageButton button;

        FavClick(MovieListModel movie, ImageButton button){
            this.movie = movie;
            this.button = button;
        }

        @Override
        public void onClick(View view) {
            if(movie.isFavorites()){
                movie.setFavorites(false);
                button.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_off));
                favoritesChooser.deleteFromFav(movie);
            }else{
                movie.setFavorites(true);
                button.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
                favoritesChooser.addedToFav(movie);
            }
        }
    }
}
