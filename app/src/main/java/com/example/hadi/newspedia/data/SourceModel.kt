package com.example.hadi.newspedia.data

import android.os.Parcel
import android.os.Parcelable
import java.net.URI

/**
 * Created by hadi on 31/01/18.
 */
data class SourceModel(
        var id: String = "",
        var name: String = "",
        var url: String = ""
): Parcelable {
    var domain: String
        get() {
            val domain = URI(url).host
            return if (domain.startsWith("www."))
                domain.substring(4)
            else domain
        }
        set(value) {this.domain = value}

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    data class Response(
            var status: String? = null,
            var sources: List<SourceModel> = ArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SourceModel> {
        override fun createFromParcel(parcel: Parcel): SourceModel {
            return SourceModel(parcel)
        }

        override fun newArray(size: Int): Array<SourceModel?> {
            return arrayOfNulls(size)
        }
    }
}