package com.epam.kotify.ui.viewmodels;

import android.location.Geocoder;
import com.epam.kotify.repository.TopsRepository;
import com.epam.kotify.utils.AppContextProvider;
import com.epam.kotify.utils.AppExecutors;
import com.epam.kotify.utils.Mappers;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TopsViewModel_Factory implements Factory<TopsViewModel> {
  private final Provider<TopsRepository> repositoryProvider;

  private final Provider<AppContextProvider> contextProvider;

  private final Provider<AppExecutors> executorsProvider;

  private final Provider<Mappers> mappersProvider;

  private final Provider<Geocoder> geocoderProvider;

  public TopsViewModel_Factory(
      Provider<TopsRepository> repositoryProvider,
      Provider<AppContextProvider> contextProvider,
      Provider<AppExecutors> executorsProvider,
      Provider<Mappers> mappersProvider,
      Provider<Geocoder> geocoderProvider) {
    this.repositoryProvider = repositoryProvider;
    this.contextProvider = contextProvider;
    this.executorsProvider = executorsProvider;
    this.mappersProvider = mappersProvider;
    this.geocoderProvider = geocoderProvider;
  }

  @Override
  public TopsViewModel get() {
    return provideInstance(
        repositoryProvider, contextProvider, executorsProvider, mappersProvider, geocoderProvider);
  }

  public static TopsViewModel provideInstance(
      Provider<TopsRepository> repositoryProvider,
      Provider<AppContextProvider> contextProvider,
      Provider<AppExecutors> executorsProvider,
      Provider<Mappers> mappersProvider,
      Provider<Geocoder> geocoderProvider) {
    return new TopsViewModel(
        repositoryProvider.get(),
        contextProvider.get(),
        executorsProvider.get(),
        mappersProvider.get(),
        geocoderProvider.get());
  }

  public static TopsViewModel_Factory create(
      Provider<TopsRepository> repositoryProvider,
      Provider<AppContextProvider> contextProvider,
      Provider<AppExecutors> executorsProvider,
      Provider<Mappers> mappersProvider,
      Provider<Geocoder> geocoderProvider) {
    return new TopsViewModel_Factory(
        repositoryProvider, contextProvider, executorsProvider, mappersProvider, geocoderProvider);
  }

  public static TopsViewModel newTopsViewModel(
      TopsRepository repository,
      AppContextProvider contextProvider,
      AppExecutors executors,
      Mappers mappers,
      Geocoder geocoder) {
    return new TopsViewModel(repository, contextProvider, executors, mappers, geocoder);
  }
}
