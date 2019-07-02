package com.egorshustov.rxfromfuturetest.main

import androidx.lifecycle.ViewModel
import com.egorshustov.rxfromfuturetest.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun makeFutureQuery() = repository.makeFutureQuery()
}