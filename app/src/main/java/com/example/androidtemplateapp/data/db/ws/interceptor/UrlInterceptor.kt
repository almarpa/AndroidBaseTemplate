package com.example.androidtemplateapp.data.db.ws.interceptor

import com.example.androidtemplateapp.BuildConfig
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URISyntaxException
import javax.inject.Singleton

@Singleton
class UrlInterceptor : Interceptor {

    companion object {
        private const val MOCK_SERVER_PORT = 3000
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var newUrl: HttpUrl? = null

        BuildConfig.BASE_URL.toHttpUrlOrNull()?.let { httpUrl ->
            try {
                newUrl = request.url.newBuilder().apply {
                    if (BuildConfig.FLAVOR == "dev") {
                        scheme("http")
                        host(httpUrl.toUrl().toURI().host)
                        port(MOCK_SERVER_PORT)
                    } else {
                        scheme(httpUrl.scheme)
                        host(httpUrl.toUrl().toURI().host)
                    }
                }.build()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            newUrl?.let {
                request = request.newBuilder()
                    .url(it)
                    .build()
            }
        }

        return chain.proceed(request)
    }
}
