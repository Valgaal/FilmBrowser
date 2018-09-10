package com.example.nikita.filmbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> mMovies;
    private Context context;
    private OnViewClicked mCallback;
    private final LayoutInflater mInflater;

    public interface OnViewClicked{
        void filmSelected(int id);
        void addedToFav(Movie movie);
        void deleteFromFav(Movie movie);
    }

    public MoviesAdapter(Context context, Fragment fragment) {
        mInflater = LayoutInflater.from(context);
        this.mCallback = (OnViewClicked) fragment;
        this.context = context;
}

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.rw_item, viewGroup, false);
        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        Movie movie = mMovies.get(i);
        moviesViewHolder.title.setText(movie.getTitle().concat(movie.getReleaseDate()));
        moviesViewHolder.ratingAvg.setText(Double.toString(movie.getRatingAvg()));
        if(movie.getPosterPath()!= null) {
            Picasso.get()
                    .load(MovieRepository.IMAGE_PATH.concat(movie.getPosterPath()))
                    .resize(150,150)
                    .centerInside()
                    .into(moviesViewHolder.poster);
        }
        moviesViewHolder.itemView.setOnClickListener(view -> mCallback.filmSelected(movie.getId()));
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

    public void setFilms(List<Movie> films){
        mMovies = films;
        notifyDataSetChanged();
    }

    public void deleteMovie(Movie movie){
        mMovies.remove(movie);
        notifyDataSetChanged();
    }


    class MoviesViewHolder extends RecyclerView.ViewHolder{

        private final ImageView poster;
        private final TextView title;
        private final TextView ratingAvg;
        private final ImageButton favButton;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.titleText);
            ratingAvg = itemView.findViewById(R.id.popularity);
            favButton = itemView.findViewById(R.id.favoritesButton);
        }
    }

    class FavClick implements View.OnClickListener{

        Movie movie;
        ImageButton button;

        FavClick(Movie movie, ImageButton button){
            this.movie = movie;
            this.button = button;
        }

        @Override
        public void onClick(View view) {
            if(movie.isFavorites()){
                movie.setFavorites(false);
                button.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_off));
                mCallback.deleteFromFav(movie);
            }else{
                movie.setFavorites(true);
                button.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
                mCallback.addedToFav(movie);
            }
        }
    }
}
