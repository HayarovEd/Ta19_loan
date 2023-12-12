package com.paydayplanner.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.paydayplanner.data.RepositoryAnalyticImpl
import com.paydayplanner.data.RepositoryServerImpl
import com.paydayplanner.data.ServiceImpl
import com.paydayplanner.data.SharedKeeperImpl
import com.paydayplanner.domain.RepositoryAnalytic
import com.paydayplanner.domain.RepositoryServer
import com.paydayplanner.domain.Service
import com.paydayplanner.domain.SharedKeeper
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