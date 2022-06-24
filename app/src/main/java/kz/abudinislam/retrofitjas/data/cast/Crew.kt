package kz.abudinislam.retrofitjas.data.cast

import com.google.gson.annotations.SerializedName


data class Crew (
    @SerializedName("adult") val adult : Boolean,
    @SerializedName("gender") val gender : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("known_for_department") val known_for_department : String,
    @SerializedName("name") val name : String,
    @SerializedName("original_name") val original_name : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("profile_path") val profile_path : String,
    @SerializedName("credit_id") val credit_id : String,
    @SerializedName("department") val department : String,
    @SerializedName("job") val job : String
)