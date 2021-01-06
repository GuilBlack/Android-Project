package mu.guillaumebl.finalproject.data

import retrofit2.Response
import retrofit2.http.GET

interface NearStationsService {
    @GET("/bus/nearstation/latlon/%2041.3985182/2.1917991/1.json")
    suspend fun getNearStationData(): Response<JsonData>
}