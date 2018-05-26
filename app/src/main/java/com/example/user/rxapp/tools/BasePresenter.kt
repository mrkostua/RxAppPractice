package com.example.user.rxapp.tools

/**
 * @author Kostiantyn Prysiazhnyi on 5/26/2018.
 */
interface BasePresenter<T> {
    fun takeView(view: T)
    fun disposeAll()
    fun start()

}