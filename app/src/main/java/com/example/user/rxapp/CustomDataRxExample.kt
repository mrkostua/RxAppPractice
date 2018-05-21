package com.example.user.rxapp

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * @author Kostiantyn Prysiazhnyi on 5/16/2018.
 */
object CustomDataRxExample {
    private val TAG = this.javaClass.simpleName

    private val bicycleObjectObserver = object : DisposableObserver<Bicycle>() {
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
    private val bikesObserver = object : DisposableObserver<String>() {
        override fun onComplete() {
            Log.d(TAG, "All items are emitted!")

        }

        override fun onNext(t: String) {
            Log.d(TAG, "Name: " + t)
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "onError: " + e.message)
        }

    }
    private val myBikesObserver = object : DisposableObserver<String>() {
        override fun onComplete() {
            Log.d(TAG, "All items are emitted!")

        }

        override fun onNext(t: String) {
            Log.d(TAG, "Name: " + t)
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "onError: " + e.message)
        }

    }

    private fun getObservableWithCustomData(): Observable<Bicycle> {
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

    private fun emitData(compositeDisposable : CompositeDisposable) {
        val bikesObservable = Observable.just("Bulls", "Trek", "Giant", "Canyon", "BTW", "sh")
        val startsWithBObservable = bikesObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    it.toLowerCase().startsWith("b")
                }.subscribeWith(bikesObserver)

        val myBikesObservable = bikesObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    it == "Bulls" || it == "sh"
                }
                .map { it + " -> is a pro bicycle \n" }
                .subscribeWith(myBikesObserver)

        compositeDisposable.add(startsWithBObservable)
        compositeDisposable.add(myBikesObservable)

    }

    private fun emitBicycleData(compositeDisposable : CompositeDisposable) {
        val myBikeObservableUpdate = CustomDataRxExample.getObservableWithCustomData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter { it.isMyBike }
                .map {
                    it.name = it.name + " best"
                    it
                }
                .subscribeWith(CustomDataRxExample.bicycleObjectObserver)
        compositeDisposable.add(myBikeObservableUpdate)
    }

    private fun getBicycles() = arrayListOf(
            Bicycle("Bulls", true),
            Bicycle("sh", true),
            Bicycle("Trek"),
            Bicycle("Giant"),
            Bicycle("Canyon"),
            Bicycle("BTW"))


}