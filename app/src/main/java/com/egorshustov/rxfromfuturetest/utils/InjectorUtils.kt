package com.egorshustov.rxfromfuturetest.utils

import com.egorshustov.rxfromfuturetest.data.Repository
import com.egorshustov.rxfromfuturetest.data.remote.ServiceGenerator
import com.egorshustov.rxfromfuturetest.main.MainViewModelFactory

object InjectorUtils {
    fun provideServiceGenerator(): ServiceGenerator {
        return ServiceGenerator.getInstance()
    }

    fun provideRepository(): Repository {
        return Repository.getInstance()
    }

    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory.getInstance()
    }
}