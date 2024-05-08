package com.example.templateapp.di

import android.content.Context
import com.example.templateapp.api.ApiInterface
import com.example.templateapp.ui.screen.dashboard.repository.DashboardRepository
import com.example.templateapp.ui.screen.dashboard.repository.DashboardRepositoryImpl
import com.example.templateapp.ui.screen.launcher.repository.LoginRepository
import com.example.templateapp.ui.screen.launcher.repository.LoginRepositoryImpl
import com.example.templateapp.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider =
        ResourceProvider(context)

    @Singleton
    @Provides
    fun provideLoginRepository(apiInterface: ApiInterface): LoginRepository =
        LoginRepositoryImpl(apiInterface)

    @Singleton
    @Provides
    fun provideDashboardRepository(apiInterface: ApiInterface): DashboardRepository =
        DashboardRepositoryImpl(apiInterface)
}