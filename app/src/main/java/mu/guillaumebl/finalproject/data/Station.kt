package mu.guillaumebl.finalproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Station (
    val id: String,
    val street_name: String,
    val city: String,
    val utm_x: String,
    val utm_y: String,
    val lat: String,
    val lon: String,
    val furniture: String,
    val buses: String,
    val distance: String
    ): Parcelable