package com.example.androidbasetemplate.data.db.ws.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class UrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}