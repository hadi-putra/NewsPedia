package com.example.hadi.newspedia

import com.example.hadi.newspedia.data.NewsModel
import com.example.hadi.newspedia.data.SourceModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by hadi on 31/01/18.
 */
interface NewsApi{

    @GET("v2/sources")
    fun getSources(): Observable<SourceModel.Response>

    @GET("v2/everything")
    fun getNews(@Query("domains") domain: String,
                @Query("q") query: String? =  null,
                @Query("page") page: Int = 1): Observable<NewsModel.Response>
}