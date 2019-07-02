package julienbirabent.apollomusic.data.local.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tablature(
    @Expose
    @SerializedName("firstString")
    var firstString: String,
    @Expose
    @SerializedName("secondString")
    var secondString: String,
    @Expose
    @SerializedName("thirdString")
    var thirdString: String,
    @Expose
    @SerializedName("fourthString")
    var fourthString: String,
    @Expose
    @SerializedName("fifthString")
    var fifthString: String,
    @Expose
    @SerializedName("sixthString")
    var sixthString: String
)
