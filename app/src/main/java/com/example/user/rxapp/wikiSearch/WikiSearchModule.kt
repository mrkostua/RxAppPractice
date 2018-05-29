package com.example.user.rxapp.wikiSearch

import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 5/29/2018.
 */
@Module
abstract class WikiSearchModule {

    @Binds
    abstract fun provideWikiSearchPresenter(p: WikiSearchPresenter): WikiSearchContract.Presenter
}