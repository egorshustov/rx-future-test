package com.egorshustov.rxfromfuturetest.data

import android.util.Log
import com.egorshustov.rxfromfuturetest.data.remote.ServiceGenerator
import com.egorshustov.rxfromfuturetest.utils.InjectorUtils
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class Repository private constructor(private val serviceGenerator: ServiceGenerator) {

    fun makeFutureQuery(): Future<Observable<ResponseBody>> {

        val executorService = Executors.newSingleThreadExecutor()

        val callable = Callable {
            Log.d(TAG, "Thread: ${Thread.currentThread().name}")
            serviceGenerator.getRequestApi().makeObservableQuery()
        }

        return object : Future<Observable<ResponseBody>> {
            override fun isDone(): Boolean {
                Log.d(TAG, "Repository: isDone: ${executorService.isTerminated}")
                return executorService.isTerminated
            }

            override fun get(): Observable<ResponseBody> {
                Log.d(TAG, "Repository: get")
                Log.d(TAG, "Thread: ${Thread.currentThread().name}")
                return executorService.submit(callable).get()
            }

            override fun get(timeout: Long, unit: TimeUnit?): Observable<ResponseBody> {
                Log.d(TAG, "Repository: get: timeout: $timeout, unit: $unit")
                return executorService.submit(callable).get(timeout, unit)
            }

            override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
                Log.d(TAG, "Repository: cancel: mayInterruptIfRunning: $mayInterruptIfRunning")
                if (mayInterruptIfRunning) {
                    executorService.shutdown()
                }
                return false
            }

            override fun isCancelled(): Boolean {
                Log.d(TAG, "Repository: isCancelled")
                return executorService.isShutdown
            }

        }
    }

    companion object {
        const val TAG = "RxFromFutureTest"

        private var INSTANCE: Repository? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(Repository::class.java) {
                INSTANCE ?: Repository(InjectorUtils.provideServiceGenerator())
                    .also { INSTANCE = it }
            }
    }
}