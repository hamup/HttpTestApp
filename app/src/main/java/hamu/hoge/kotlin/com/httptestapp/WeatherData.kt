package hamu.hoge.kotlin.com.httptestapp


data class WeatherData(
        val pinpointLocations: List<PinpointLocation>,
        val link: String,
        val forecasts: List<Forecast>,
        val location: Location,
        val publicTime: String,
        val copyright: Copyright,
        val title: String,
        val description: Description
) {

    data class Copyright(
            val provider: List<Provider>,
            val link: String,
            val title: String,
            val image: Image
    ) {

        data class Image(
                val width: Int,
                val link: String,
                val url: String,
                val title: String,
                val height: Int
        )


        data class Provider(
                val link: String,
                val name: String
        )
    }


    data class PinpointLocation(
            val link: String,
            val name: String
    )


    data class Location(
            val city: String,
            val area: String,
            val prefecture: String
    )


    data class Description(
            val text: String,
            val publicTime: String
    )


    data class Forecast(
            val dateLabel: String,
            val telop: String,
            val date: String,
            val temperature: Temperature,
            val image: Image
    ) {

        data class Temperature(
                val min: Any,
                val max: Any
        )


        data class Image(
                val width: Int,
                val url: String,
                val title: String,
                val height: Int
        )
    }
}