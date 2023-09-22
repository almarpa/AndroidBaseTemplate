package com.example.androidbasetemplate.data.db.ws.interceptor

import com.example.androidbasetemplate.BuildConfig
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
        with(chain.request()) {
            var newUrl: HttpUrl? = null
            try {
                newUrl = url.newBuilder().apply {
                    BuildConfig.BASE_URL.toHttpUrlOrNull()?.apply {
                        if (BuildConfig.FLAVOR == "dev") {
                            scheme("http")
                            host(url.toUrl().toURI().host)
                            port(MOCK_SERVER_PORT)
                        } else {
                            scheme(scheme)
                            host(toUrl().toURI().host)
                        }
                    }
                }.build()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }

            newUrl?.let {
                newBuilder()
                    .url(it)
                    .build()
            }

            return chain.proceed(this)
        }
    }
}
