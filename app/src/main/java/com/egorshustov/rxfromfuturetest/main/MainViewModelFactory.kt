package com.egorshustov.rxfromfuturetest.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egorshustov.rxfromfuturetest.data.Repository
import com.egorshustov.rxfromfuturetest.utils.InjectorUtils

class MainViewModelFactory private constructor(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

    companion object {
        @Volatile
        private var INSTANCE: MainViewModelFactory? = null

        fun getInstance() =
            INSTANCE ?: synchronized(MainViewModelFactory::class.java) {
                INSTANCE ?: MainViewModelFactory(InjectorUtils.provideRepository())
                    .also { INSTANCE = it }
            }
    }
}