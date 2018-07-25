package hamu.hoge.kotlin.com.httptestapp

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : RxAppCompatActivity() {

    val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textview = findViewById<TextView>(R.id.textView)
        val getbutton = findViewById<Button>(R.id.get_button)
        val handler = Handler()

        // Gson & Retrofit Builder
        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://weather.livedoor.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val weatherAPI = retrofit.create(IWeatherAPI::class.java)

        getbutton.setOnClickListener {
            //run("http://weather.livedoor.com/forecast/webservice/json/v1?city=200010", handler, textview)
            weatherAPI.getWeather("200010")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindToLifecycle(this)
                    .subscribe({ res -> textview.setText(res.forecasts[0].telop.toString()) })
        }
    }

    // OkHttp Builder
    fun run(url: String, handler: Handler, textView: TextView) {
        val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .get()
                .build()

        httpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                //TODO : Add Error Handling
            }
            override fun onResponse(call: Call, response: Response) {
                val responseText: String? = response.body()?.string()
                handler.post {
                    textView.text = responseText
                }
            }
        })
    }
}
