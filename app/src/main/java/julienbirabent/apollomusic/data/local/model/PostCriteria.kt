package julienbirabent.apollomusic.data.local.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostCriteria(

    @Expose
    @SerializedName("criteria")
    var criteria: String,
    @Expose
    @SerializedName("profileId")
    var profileId: String?
)