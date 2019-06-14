package com.epam.kotify.di;

import android.app.Activity;
import android.app.Application;
import android.location.Geocoder;
import androidx.lifecycle.ViewModel;
import com.epam.kotify.App;
import com.epam.kotify.App_MembersInjector;
import com.epam.kotify.api.CountryTopService;
import com.epam.kotify.db.AppDatabase;
import com.epam.kotify.db.TopsDao;
import com.epam.kotify.repository.TopsRepository_Factory;
import com.epam.kotify.ui.AddToLovedDialog;
import com.epam.kotify.ui.AddToLovedDialog_MembersInjector;
import com.epam.kotify.ui.MainActivity;
import com.epam.kotify.ui.MainActivity_MembersInjector;
import com.epam.kotify.ui.MapFragment;
import com.epam.kotify.ui.MapFragment_MembersInjector;
import com.epam.kotify.ui.RemoveFromLovedDialog;
import com.epam.kotify.ui.RemoveFromLovedDialog_MembersInjector;
import com.epam.kotify.ui.SettingsActivity;
import com.epam.kotify.ui.SettingsActivity_MembersInjector;
import com.epam.kotify.ui.artistview.LovedArtistsFragment;
import com.epam.kotify.ui.artistview.LovedArtistsFragment_MembersInjector;
import com.epam.kotify.ui.artistview.TopArtistsFragment;
import com.epam.kotify.ui.artistview.TopArtistsFragment_MembersInjector;
import com.epam.kotify.ui.tracksview.LovedTracksFragment;
import com.epam.kotify.ui.tracksview.LovedTracksFragment_MembersInjector;
import com.epam.kotify.ui.tracksview.TopTracksFragment;
import com.epam.kotify.ui.tracksview.TopTracksFragment_MembersInjector;
import com.epam.kotify.ui.viewmodels.TopsViewModel;
import com.epam.kotify.ui.viewmodels.TopsViewModel_Factory;
import com.epam.kotify.utils.AppContextProvider;
import com.epam.kotify.utils.AppExecutors;
import com.epam.kotify.utils.AppExecutors_Factory;
import com.epam.kotify.utils.ConnectionManager;
import com.epam.kotify.utils.Mappers;
import com.epam.kotify.utils.Mappers_Factory;
import com.epam.kotify.utils.ThemeManager;
import com.epam.kotify.utils.ThemeManager_Factory;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<AppExecutors> appExecutorsProvider;

  private Provider<Application> applicationProvider;

  private Provider<AppDatabase> provideDbProvider;

  private Provider<TopsDao> provideTopsDaoProvider;

  private Provider<CountryTopService> provideCountryTopServiceProvider;

  private Provider<ConnectionManager> provideConnectionManagerProvider;

  private TopsRepository_Factory topsRepositoryProvider;

  private Provider<AppContextProvider> provideAppContextProvider;

  private Provider<Mappers> mappersProvider;

  private Provider<Geocoder> provideGeocoderProvider;

  private TopsViewModel_Factory topsViewModelProvider;

  private Provider<ThemeManager> themeManagerProvider;

  private DaggerAppComponent(Builder builder) {

    initialize(builder);
  }

  public static AppComponent.Builder builder() {
    return new Builder();
  }

  private DispatchingAndroidInjector<Activity> getDispatchingAndroidInjectorOfActivity() {
    return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(
        Collections
            .<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>>
                emptyMap());
  }

  private Map<Class<? extends ViewModel>, Provider<ViewModel>>
      getMapOfClassOfAndProviderOfViewModel() {
    return Collections.<Class<? extends ViewModel>, Provider<ViewModel>>singletonMap(
        TopsViewModel.class, (Provider) topsViewModelProvider);
  }

  private TopsViewModelFactory getTopsViewModelFactory() {
    return new TopsViewModelFactory(getMapOfClassOfAndProviderOfViewModel());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.appExecutorsProvider = DoubleCheck.provider(AppExecutors_Factory.create());
    this.applicationProvider = InstanceFactory.create(builder.application);
    this.provideDbProvider =
        DoubleCheck.provider(
            AppModule_ProvideDbFactory.create(builder.appModule, applicationProvider));
    this.provideTopsDaoProvider =
        DoubleCheck.provider(
            AppModule_ProvideTopsDaoFactory.create(builder.appModule, provideDbProvider));
    this.provideCountryTopServiceProvider =
        DoubleCheck.provider(
            AppModule_ProvideCountryTopServiceFactory.create(
                builder.appModule, applicationProvider));
    this.provideConnectionManagerProvider =
        DoubleCheck.provider(
            AppModule_ProvideConnectionManagerFactory.create(
                builder.appModule, applicationProvider));
    this.topsRepositoryProvider =
        TopsRepository_Factory.create(
            appExecutorsProvider,
            provideDbProvider,
            provideTopsDaoProvider,
            provideCountryTopServiceProvider,
            provideConnectionManagerProvider);
    this.provideAppContextProvider =
        DoubleCheck.provider(
            AppModule_ProvideAppContextProviderFactory.create(
                builder.appModule, applicationProvider));
    this.mappersProvider = DoubleCheck.provider(Mappers_Factory.create(appExecutorsProvider));
    this.provideGeocoderProvider =
        DoubleCheck.provider(
            AppModule_ProvideGeocoderFactory.create(builder.appModule, applicationProvider));
    this.topsViewModelProvider =
        TopsViewModel_Factory.create(
            topsRepositoryProvider,
            provideAppContextProvider,
            appExecutorsProvider,
            mappersProvider,
            provideGeocoderProvider);
    this.themeManagerProvider =
        DoubleCheck.provider(ThemeManager_Factory.create(provideAppContextProvider));
  }

  @Override
  public void inject(App app) {
    injectApp(app);
  }

  @Override
  public void inject(MainActivity activity) {
    injectMainActivity(activity);
  }

  @Override
  public void inject(TopArtistsFragment fragment) {
    injectTopArtistsFragment(fragment);
  }

  @Override
  public void inject(TopTracksFragment fragment) {
    injectTopTracksFragment(fragment);
  }

  @Override
  public void inject(MapFragment fragment) {
    injectMapFragment(fragment);
  }

  @Override
  public void inject(SettingsActivity activity) {
    injectSettingsActivity(activity);
  }

  @Override
  public void inject(AddToLovedDialog fragment) {
    injectAddToLovedDialog(fragment);
  }

  @Override
  public void inject(LovedArtistsFragment fragment) {
    injectLovedArtistsFragment(fragment);
  }

  @Override
  public void inject(LovedTracksFragment fragment) {
    injectLovedTracksFragment(fragment);
  }

  @Override
  public void inject(RemoveFromLovedDialog fragment) {
    injectRemoveFromLovedDialog(fragment);
  }

  private App injectApp(App instance) {
    App_MembersInjector.injectDispatchingActivityInjector(
        instance, getDispatchingAndroidInjectorOfActivity());
    return instance;
  }

  private MainActivity injectMainActivity(MainActivity instance) {
    MainActivity_MembersInjector.injectViewModelFactory(instance, getTopsViewModelFactory());
    MainActivity_MembersInjector.injectThemeManager(instance, themeManagerProvider.get());
    return instance;
  }

  private TopArtistsFragment injectTopArtistsFragment(TopArtistsFragment instance) {
    TopArtistsFragment_MembersInjector.injectViewModelFactory(instance, getTopsViewModelFactory());
    return instance;
  }

  private TopTracksFragment injectTopTracksFragment(TopTracksFragment instance) {
    TopTracksFragment_MembersInjector.injectViewModelFactory(instance, getTopsViewModelFactory());
    return instance;
  }

  private MapFragment injectMapFragment(MapFragment instance) {
    MapFragment_MembersInjector.injectViewModelFactory(instance, getTopsViewModelFactory());
    return instance;
  }

  private SettingsActivity injectSettingsActivity(SettingsActivity instance) {
    SettingsActivity_MembersInjector.injectThemeManager(instance, themeManagerProvider.get());
    return instance;
  }

  private AddToLovedDialog injectAddToLovedDialog(AddToLovedDialog instance) {
    AddToLovedDialog_MembersInjector.injectFactory(instance, getTopsViewModelFactory());
    return instance;
  }

  private LovedArtistsFragment injectLovedArtistsFragment(LovedArtistsFragment instance) {
    LovedArtistsFragment_MembersInjector.injectFactory(instance, getTopsViewModelFactory());
    return instance;
  }

  private LovedTracksFragment injectLovedTracksFragment(LovedTracksFragment instance) {
    LovedTracksFragment_MembersInjector.injectFactory(instance, getTopsViewModelFactory());
    return instance;
  }

  private RemoveFromLovedDialog injectRemoveFromLovedDialog(RemoveFromLovedDialog instance) {
    RemoveFromLovedDialog_MembersInjector.injectFactory(instance, getTopsViewModelFactory());
    return instance;
  }

  private static final class Builder implements AppComponent.Builder {
    private AppModule appModule;

    private Application application;

    @Override
    public AppComponent build() {
      if (appModule == null) {
        this.appModule = new AppModule();
      }
      Preconditions.checkBuilderRequirement(application, Application.class);
      return new DaggerAppComponent(this);
    }

    @Override
    public Builder application(Application app) {
      this.application = Preconditions.checkNotNull(app);
      return this;
    }
  }
}
