package com.example.nikita.filmbrowser.Di;

import com.example.nikita.filmbrowser.Model.Network.NetworkRequestWork;
import com.example.nikita.filmbrowser.UI.Details.DetailsViewModel;
import com.example.nikita.filmbrowser.UI.Favorites.FavoritesViewModel;
import com.example.nikita.filmbrowser.UI.Search.SearchViewModel;
import com.example.nikita.filmbrowser.UI.Trending.TrendingViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {RepositoryModule.class})
@Singleton
public interface AppComponent {
    void inject(NetworkRequestWork work);
    void inject(DetailsViewModel viewModel);
    void inject(FavoritesViewModel viewModel);
    void inject(TrendingViewModel viewModel);
    void inject(SearchViewModel viewModel);
}
