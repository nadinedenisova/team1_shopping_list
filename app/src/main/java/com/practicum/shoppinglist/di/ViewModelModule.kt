package com.practicum.shoppinglist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.shoppinglist.auth.viewmodel.LoginScreenViewModel
import com.practicum.shoppinglist.auth.viewmodel.RecoveryScreenViewModel
import com.practicum.shoppinglist.auth.viewmodel.RegistrationScreenViewModel
import com.practicum.shoppinglist.core.presentation.ui.FabViewModel
import com.practicum.shoppinglist.details.presentation.viewmodel.DetailsViewModel
import com.practicum.shoppinglist.di.api.DaggerViewModelFactory
import com.practicum.shoppinglist.di.api.ViewModelKey
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindMainActivityViewModel(
        myViewModel: MainScreenViewModel,
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationScreenViewModel::class)
    abstract fun bindRegistrationViewModel(
        registrationViewModel: RegistrationScreenViewModel,
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginScreenViewModel::class)
    abstract fun bindLoginScreenViewModel(
        registrationViewModel: LoginScreenViewModel,
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecoveryScreenViewModel::class)
    abstract fun bindRecoveryScreenViewModel(
        recoveryScreenViewModel: RecoveryScreenViewModel,
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsScreenViewModel(
        viewModel: DetailsViewModel,
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FabViewModel::class)
    abstract fun bindFabViewModel(
        viewModel: FabViewModel,
    ): ViewModel
}