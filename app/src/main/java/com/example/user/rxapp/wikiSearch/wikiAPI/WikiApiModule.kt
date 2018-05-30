package com.example.user.rxapp.wikiSearch.wikiAPI

import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 5/30/2018.
 */
@Module
class WikiApiModule {
    @Provides
    fun provideWikiApiService() = WikiApiService.create()
}