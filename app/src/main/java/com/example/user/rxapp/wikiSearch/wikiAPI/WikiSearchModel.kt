package com.example.user.rxapp.wikiSearch.wikiAPI

import com.google.gson.annotations.SerializedName

/**
 * @author Kostiantyn Prysiazhnyi on 5/30/2018.
 */
object WikiSearchModel {
    data class Result(@SerializedName("query") val query: Query)
    data class Query(@SerializedName("search")val search: Array<SearchDo>)
}