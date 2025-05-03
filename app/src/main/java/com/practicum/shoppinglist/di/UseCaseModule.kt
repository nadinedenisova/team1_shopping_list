package com.practicum.shoppinglist.di

import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import com.practicum.shoppinglist.main.domain.impl.AddShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ChangeThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.GetThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveAllShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListByNameUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.UpdateShoppingListUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideShowShoppingListsUseCase(repository: MainScreenRepository): ShowShoppingListsUseCase {
        return ShowShoppingListsUseCase(repository)
    }

    @Provides
    fun provideShowShoppingListByNameUseCase(repository: MainScreenRepository): ShowShoppingListByNameUseCase {
        return ShowShoppingListByNameUseCase(repository)
    }

    @Provides
    fun provideAddShoppingListUseCase(repository: MainScreenRepository): AddShoppingListUseCase {
        return AddShoppingListUseCase(repository)
    }

    @Provides
    fun provideUpdateShoppingListUseCase(repository: MainScreenRepository): UpdateShoppingListUseCase {
        return UpdateShoppingListUseCase(repository)
    }

    @Provides
    fun provideRemoveShoppingListUseCase(repository: MainScreenRepository): RemoveShoppingListUseCase {
        return RemoveShoppingListUseCase(repository)
    }

    @Provides
    fun provideRemoveAllShoppingListsUseCase(repository: MainScreenRepository): RemoveAllShoppingListsUseCase {
        return RemoveAllShoppingListsUseCase(repository)
    }

    @Provides
    fun provideChangeThemeSettingsUseCase(repository: MainScreenRepository): ChangeThemeSettingsUseCase {
        return ChangeThemeSettingsUseCase(repository)
    }

    @Provides
    fun provideGetThemeSettingsUseCase(repository: MainScreenRepository): GetThemeSettingsUseCase {
        return GetThemeSettingsUseCase(repository)
    }
}