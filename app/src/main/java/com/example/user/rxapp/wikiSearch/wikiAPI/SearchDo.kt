package com.example.user.rxapp.wikiSearch.wikiAPI

import com.google.gson.annotations.SerializedName

class SearchDo(@SerializedName("ns") private val ns: Int,
               @SerializedName("title") private val title: String,
               @SerializedName("pageid") private val pageid: Int,
               @SerializedName("size") private val size: Int,
               @SerializedName("wordcount") private val wordcount: Int,
               @SerializedName("snippet") private val snippet: String,
               @SerializedName("timestamp") private val timestamp: String){

    override fun toString(): String {
        return "SearchDo(ns=$ns, title='$title', pageid=$pageid, size=$size, wordcount=$wordcount, snippet='$snippet', timestamp='$timestamp')"
    }
}

