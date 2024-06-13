package com.paydayplanner.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.paydayplanner.app.data.RepositoryAnalyticImpl
import com.paydayplanner.app.data.RepositoryServerImpl
import com.paydayplanner.app.data.ServiceImpl
import com.paydayplanner.app.data.SharedKeeperImpl
import com.paydayplanner.app.domain.RepositoryAnalytic
import com.paydayplanner.app.domain.RepositoryServer
import com.paydayplanner.app.domain.Service
import com.paydayplanner.app.domain.SharedKeeper
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DiModule {

    @Binds
    @Singleton
    abstract fun bindService(service: ServiceImpl): Service

    @Binds
    @Singleton
    abstract fun bindKeeper(sharedKeeper: SharedKeeperImpl): SharedKeeper

    @Binds
    @Singleton
    abstract fun bindRepositoryAnalytic(repository: RepositoryAnalyticImpl): RepositoryAnalytic

    @Binds
    @Singleton
    abstract fun bindRepositoryServer(repository: RepositoryServerImpl): RepositoryServer

}