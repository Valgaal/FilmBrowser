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

import com.example.nikita.filmbrowser.Models.SearchResultModel;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<SearchResultModel> mMovies;
    private Context context;
    private OnViewClicked mCallback;
    private final LayoutInflater mInflater;

    public interface OnViewClicked{
        void filmSelected(int id);
        void addedToFav(SearchResultModel id);
        void deleteFromFav(SearchResultModel id);
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
        SearchResultModel movie = mMovies.get(i);
        if(movie.getTitle() == null){
            moviesViewHolder.title.setText(movie.getName().concat(movie.convertReleaseDate()));
        }else{
            moviesViewHolder.title.setText(movie.getTitle().concat(movie.convertReleaseDate()));
        }
        moviesViewHolder.ratingAvg.setText(Double.toString(movie.getVoteAverage()));
        if(movie.getPosterPath()!= null) {
            Picasso.get()
                    .load(MovieRepository.IMAGE_PATH.concat(movie.getPosterPath()))
                    .resize(200,200)
                    .centerInside()
                    .into(moviesViewHolder.poster);
        }
        moviesViewHolder.itemView.setOnClickListener(view -> mCallback.filmSelected(movie.getId()));
        moviesViewHolder.favButton.setOnClickListener(new FavClick(movie, moviesViewHolder.favButton));
    }

    @Override
    public int getItemCount() {
        if (mMovies != null)
            return mMovies.size();
        else return 0;
    }

    public void setFilms(List<SearchResultModel> films){
        mMovies = films;
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
            ratingAvg = itemView.findViewById(R.id.rating);
            favButton = itemView.findViewById(R.id.favoritesButton);
        }
    }

    class FavClick implements View.OnClickListener{

        int count;
        SearchResultModel movie;
        ImageButton button;

        FavClick(SearchResultModel movie, ImageButton button){
            this.movie = movie;
            this.button = button;
        }

        @Override
        public void onClick(View view) {
            count++;
            if(count %2 ==0){ //not pressed
                mCallback.deleteFromFav(movie);
                button.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_off));
            }else{ //pressed
                mCallback.addedToFav(movie);
                button.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
            }

        }
    }
}