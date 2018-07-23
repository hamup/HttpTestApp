package hamu.hoge.kotlin.com.httptestapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textview = findViewById<TextView>(R.id.textView)
        val getbutton = findViewById<Button>(R.id.get_button)
        val handler = Handler()

        getbutton.setOnClickListener {
            run("https://everyday-growth.com/name/api/?cnt=2", handler, textview)
        }
    }

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
