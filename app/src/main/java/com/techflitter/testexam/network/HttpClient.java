package com.techflitter.testexam.network;



import com.techflitter.testexam.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class HttpClient {

    public static final long CONNECTION_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(1);
    public static final long READ_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(1);
    public static final long WRITE_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(1);

    private static OkHttpClient client;

    public static synchronized OkHttpClient getInstance() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    .writeTimeout(WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }
            client = builder.build();
        }
        return client;
    }
}
