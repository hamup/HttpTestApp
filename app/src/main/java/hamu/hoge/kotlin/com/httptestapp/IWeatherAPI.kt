package hamu.hoge.kotlin.com.httptestapp

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

internal interface IWeatherAPI {
    @GET("forecast/webservice/json/v1")
    fun getWeather(@Query("city") city: String): Observable<WeatherData>

}
