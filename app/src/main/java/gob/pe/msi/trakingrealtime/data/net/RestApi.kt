package gob.pe.msi.trakingrealtime.data.net

import gob.pe.msi.trakingrealtime.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RestApi {

    private var retrofit: Retrofit? = null
    private var retrofitLocal: Retrofit? = null
    /*fun createRetrofitInstance(baseUrl: String? = Constants.BASE_URL_REMOTO): Retrofit? {
        println("RestApi createRetrofitInstance ======")
        val interceptor = HttpLoggingInterceptor { message ->
            println("RestApi HttpLoggingInterceptor 1111 ======> $message")
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }*/

    val retrofitInstance: Retrofit?
    get() {
        println("RestApi  retrofitInstance =====")
        val interceptor = HttpLoggingInterceptor { message ->
            println("RestApi HttpLoggingInterceptor 222 ======> $message")
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_REMOTO)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    val retrofitInstanceLocal: Retrofit?
        get() {
            println("RestApi  retrofitInstance =====")
            val interceptor = HttpLoggingInterceptor { message ->
                println("RestApi HttpLoggingInterceptor 222 ======> $message")
            }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofitLocal == null) {
                retrofitLocal = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL_REMOTO_WEB_LOCAL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
            }
            return retrofitLocal
        }

}