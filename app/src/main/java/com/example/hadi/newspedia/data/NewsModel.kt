package com.example.hadi.newspedia.data

import android.os.Parcel
import android.os.Parcelable
import java.text.DateFormat
import java.util.*

/**
 * Created by hadi on 31/01/18.
 */
data class NewsModel(
        var source: SourceModel? = null,
        var author: String? = null,
        var title: String? = null,
        var url: String? = null,
        var description: String? = null,
        var urlToImage: String? = null,
        var publishedAt: Date? = null
): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(SourceModel::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readSerializable() as Date)

    data class Response(
            var status: String? = null,
            var totalResults: Int = 0,
            var articles: List<NewsModel> = ArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(description)
        parcel.writeString(urlToImage)
        parcel.writeSerializable(publishedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsModel> {
        override fun createFromParcel(parcel: Parcel): NewsModel {
            return NewsModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsModel?> {
            return arrayOfNulls(size)
        }
    }
}