package com.example.mygithub.net

import android.util.Log
import com.example.MyGitHub.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class LogInterceptor : Interceptor {

    private val UTF8: Charset = StandardCharsets.UTF_8

    override fun intercept(chain: Interceptor.Chain): Response {

        //获得请求信息，此处如有需要可以添加headers信息
        val request = chain.request()
        //发送请求，获得相应，
        val response = chain.proceed(request)

        //debug包 打印请求信息
        if (BuildConfig.DEBUG) {
            //记录请求耗时
            val startNs = System.nanoTime()
            //使用response获得headers(),可以更新本地Cookie。
            val headers = request.headers


            //获得返回的body，注意此处不要使用responseBody.string()获取返回数据
            // 原因在于这个方法会消耗返回结果的数据(buffer)response
            val responseBody = response.body ?: return response

            //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

            //获得返回的数据
            val buffer = source.buffer

            //使用前clone()下，避免直接消耗
            val result = buffer.clone().readString(StandardCharsets.UTF_8)

            val map = LinkedHashMap<String, String>()
            map["url"] = request.url.toString()
            map["method"] = request.method
            if (request.body != null) {
                val body: String
                val requestBody = request.body

                val tbuffer = Buffer()
                requestBody?.writeTo(tbuffer)

                var charset: Charset? = UTF8
                val contentType = requestBody?.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                if (charset != null) {
                    body = tbuffer.readString(charset)
                    map["body"] = body
                }
            }
            map["header"] = headers.toString()
            map["cost"] = tookMs.toString() + "ms"


            val logStr = GsonBuilder().disableHtmlEscaping().create().toJson(map)
            Log.d("LogInterceptor:", "$logStr\n response:$result")
        }
        return response
    }
}