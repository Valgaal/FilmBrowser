package com.example.nikita.filmbrowser.Di;

import com.example.nikita.filmbrowser.Domain.Interactors.Details.DetailsInteractor;
import com.example.nikita.filmbrowser.Domain.Interactors.Favorites.FavoritesInteractor;
import com.example.nikita.filmbrowser.Domain.Interactors.Search.SearchInteractor;
import com.example.nikita.filmbrowser.Domain.Interactors.Trending.TrendingInteractor;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {RepositoryModule.class})
@Singleton
public interface AppComponent {
    void inject(TrendingInteractor trendingInteractor);

    void inject(DetailsInteractor viewModel);

    void inject(FavoritesInteractor viewModel);

    void inject(SearchInteractor viewModel);
}
