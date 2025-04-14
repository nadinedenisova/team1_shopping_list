package com.practicum.shoppinglist.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.practicum.shoppinglist.MainScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModule::class,
    InteractorModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
])
interface AppComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(appContext: Context): Builder
        fun build(): AppComponent
    }

    //fun inject(app: App)
    //fun inject(activity: MainActivity)
    fun inject(viewModel: MainScreenViewModel)
}