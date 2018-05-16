package com.example.user.rxapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private lateinit var disposable: Disposable
    private val compositeDisposable = CompositeDisposable()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emitData()
        emitBicycleData()

    }


    private fun emitData() {
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

    private fun emitBicycleData() {
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}
