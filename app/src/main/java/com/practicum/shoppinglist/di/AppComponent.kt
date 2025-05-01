package com.practicum.shoppinglist.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.practicum.shoppinglist.core.presentation.ui.FabViewModel
import com.practicum.shoppinglist.details.presentation.viewmodel.DetailsViewModel
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DatabaseModule::class,
    BindDataModule::class,
    ProvideDataModule::class,
    UseCaseModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    NetworkModule::class,
])
interface AppComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(appContext: Context): Builder
        fun build(): AppComponent
    }

    fun inject(viewModel: MainScreenViewModel)
    fun inject(viewModel: DetailsViewModel)
    fun inject(viewModel: FabViewModel)
}