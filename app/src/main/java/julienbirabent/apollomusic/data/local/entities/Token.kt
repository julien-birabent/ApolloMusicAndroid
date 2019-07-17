package julienbirabent.apollomusic.data.local.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Token(
    @Expose
    @SerializedName("id")
    var token: String,
    @Expose
    @SerializedName("userId")
    var userId: String,
    @Expose
    @SerializedName("created")
    var created: Date,

    @Expose
    @SerializedName("ttl")
    var ttl: Long
) {
    constructor() : this("", "", Date(), 0)
}
