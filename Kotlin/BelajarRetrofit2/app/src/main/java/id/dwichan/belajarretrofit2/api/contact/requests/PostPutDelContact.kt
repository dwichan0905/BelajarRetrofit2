package id.dwichan.belajarretrofit2.api.contact.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.dwichan.belajarretrofit2.model.Contact
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostPutDelContact(
    @SerializedName("status")
    var status: String? = null,

    @SerializedName("result")
    var mContact: Contact,

    @SerializedName("message")
    var message: String? = null
) : Parcelable