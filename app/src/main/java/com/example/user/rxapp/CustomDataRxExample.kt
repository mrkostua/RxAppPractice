package com.example.user.rxapp

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.observers.DisposableObserver

/**
 * @author Kostiantyn Prysiazhnyi on 5/16/2018.
 */
object CustomDataRxExample {
    private val TAG = this.javaClass.simpleName

    val bicycleObjectObserver = object : DisposableObserver<Bicycle>() {
        override fun onNext(t: Bicycle) {
            Log.d(TAG, "Name: " + t.name)
        }

        override fun onComplete() {
            Log.d(TAG, "All items are emitted!")

        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "onError: " + e.message)
        }

    }

    fun getObservableWithCustomData(): Observable<Bicycle> {
        val allBicycle = getBicycles()

        return Observable.create(object : ObservableOnSubscribe<Bicycle> {
            override fun subscribe(emitter: ObservableEmitter<Bicycle>) {
                allBicycle.forEach {
                    if (!emitter.isDisposed) {
                        emitter.onNext(it)
                    }
                }
                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            }

        })
    }

    private fun getBicycles() = arrayListOf(
            Bicycle("Bulls", true),
            Bicycle("sh", true),
            Bicycle("Trek"),
            Bicycle("Giant"),
            Bicycle("Canyon"),
            Bicycle("BTW"))


}