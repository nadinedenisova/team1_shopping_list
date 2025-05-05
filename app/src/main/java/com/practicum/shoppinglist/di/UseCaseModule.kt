package com.practicum.shoppinglist.di

import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository
import com.practicum.shoppinglist.details.domain.impl.AddItemOrderUseCase
import com.practicum.shoppinglist.details.domain.impl.AddProductUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteAllProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteCompletedProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteProductUseCase
import com.practicum.shoppinglist.details.domain.impl.GetProductListUseCase
import com.practicum.shoppinglist.details.domain.impl.GetProductSortOrderUseCase
import com.practicum.shoppinglist.details.domain.impl.UpdateProductUseCase
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import com.practicum.shoppinglist.main.domain.api.SettingsRepository
import com.practicum.shoppinglist.main.domain.api.TokenStorage
import com.practicum.shoppinglist.main.domain.impl.AddShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ChangeThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.CopyShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.GetThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.IsUserLoggedInUseCase
import com.practicum.shoppinglist.main.domain.impl.LoginUseCase
import com.practicum.shoppinglist.main.domain.impl.LogoutUseCase
import com.practicum.shoppinglist.main.domain.impl.RegistrationUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveAllShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListByNameUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.TokenValidationUseCase
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
    fun provideCopyShoppingListUseCase(repository: MainScreenRepository): CopyShoppingListUseCase {
        return CopyShoppingListUseCase(repository)
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
    fun provideChangeThemeSettingsUseCase(repository: SettingsRepository): ChangeThemeSettingsUseCase {
        return ChangeThemeSettingsUseCase(repository)
    }

    @Provides
    fun provideGetThemeSettingsUseCase(repository: SettingsRepository): GetThemeSettingsUseCase {
        return GetThemeSettingsUseCase(repository)
    }

    @Provides
    fun provideGetProductListUseCase(repository: DetailsScreenRepository): GetProductListUseCase {
        return GetProductListUseCase(repository)
    }

    @Provides
    fun provideAddProductUseCase(repository: DetailsScreenRepository): AddProductUseCase {
        return AddProductUseCase(repository)
    }

    @Provides
    fun provideUpdateProductUseCase(repository: DetailsScreenRepository): UpdateProductUseCase {
        return UpdateProductUseCase(repository)
    }
    @Provides
    fun provideDeleteAllProductsUseCase(repository: DetailsScreenRepository): DeleteAllProductsUseCase {
        return DeleteAllProductsUseCase(repository)
    }
    @Provides
    fun provideDeleteCompletedProductsUseCase(repository: DetailsScreenRepository): DeleteCompletedProductsUseCase {
        return DeleteCompletedProductsUseCase(repository)
    }

    @Provides
    fun provideDeleteProductUseCase(repository: DetailsScreenRepository): DeleteProductUseCase {
        return DeleteProductUseCase(repository)
    }
    @Provides
    fun provideAddItemOrderUseCase(repository: DetailsScreenRepository): AddItemOrderUseCase {
        return AddItemOrderUseCase(repository)
    }
    @Provides
    fun provideGetProductSortOrderUseCase(repository: DetailsScreenRepository): GetProductSortOrderUseCase {
        return GetProductSortOrderUseCase(repository)
    }

    @Provides
    fun provideRegistrationUseCase(repository: AuthorizationRepository): RegistrationUseCase {
        return RegistrationUseCase(repository)
    }

    @Provides
    fun provideLoginUseCase(repository: AuthorizationRepository, tokenStorage: TokenStorage): LoginUseCase {
        return LoginUseCase(repository, tokenStorage)
    }

    @Provides
    fun provideLogoutUseCase(tokenStorage: TokenStorage): LogoutUseCase {
        return LogoutUseCase(tokenStorage)
    }

    @Provides
    fun provideIsUserLoggedInUseCase(tokenStorage: TokenStorage): IsUserLoggedInUseCase {
        return IsUserLoggedInUseCase(tokenStorage)
    }

    @Provides
    fun provideTokenValidationUseCase(repository: AuthorizationRepository, tokenStorage: TokenStorage): TokenValidationUseCase {
        return TokenValidationUseCase(tokenStorage, repository)
    }
}