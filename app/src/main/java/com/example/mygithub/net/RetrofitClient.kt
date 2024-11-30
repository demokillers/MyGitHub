package com.example.mygithub.net

import com.example.mygithub.utils.APPConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RetrofitClient(baseUrl: String) {
    private var mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .client(getUnsafeOkHttpClient())
            .baseUrl(baseUrl) //设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
            .build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val trustManager: X509TrustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            val okHttpBuilder = OkHttpClient.Builder()
                .addInterceptor(LogInterceptor())
                .connectTimeout(APPConfig.API_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(APPConfig.API_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(APPConfig.API_WRITE_TIME_OUT, TimeUnit.SECONDS)
            okHttpBuilder.sslSocketFactory(sslSocketFactory, trustManager)
            okHttpBuilder.hostnameVerifier { hostname, session -> true }

            return okHttpBuilder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun <T> createService(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}