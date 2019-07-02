package com.egorshustov.rxfromfuturetest.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.egorshustov.rxfromfuturetest.R
import com.egorshustov.rxfromfuturetest.utils.InjectorUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel =
            ViewModelProviders.of(this, InjectorUtils.provideMainViewModelFactory()).get(MainViewModel::class.java)

        val disposable = mainViewModel.makeFutureQuery().get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "got the response from server!")
                try {
                    Log.d(TAG, "Thread: ${Thread.currentThread().name}")
                    Log.d(TAG, it.string())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
    }

    companion object {
        const val TAG = "RxFromFutureTest"
    }
}
