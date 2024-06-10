package gob.pe.msi.trakingrealtime.data.net

import gob.pe.msi.trakingrealtime.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestApi {
    private var retrofit: Retrofit? = null

    val retrofitInstance: Retrofit?
    get() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_REMOTO)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    /*fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }*/
}