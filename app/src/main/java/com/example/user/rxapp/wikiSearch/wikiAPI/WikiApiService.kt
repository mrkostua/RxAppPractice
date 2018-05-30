package com.example.user.rxapp.wikiSearch.wikiAPI

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Kostiantyn Prysiazhnyi on 5/30/2018.
 */
interface WikiApiService {
    @GET("api.php")
    fun getContent(@Query("action") action: String,
                   @Query("format") format: String,
                   @Query("list") list: String,
                   @Query("srsearch") srsearch: String): Single<WikiSearchModel.Result>


    companion object {
        fun create(): WikiApiService {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://en.wikipedia.org/w/")
                    .build().create(WikiApiService::class.java)
        }
    }
}