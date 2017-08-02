package cn.octopus.core.service;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.octopus.core.BuildConfig;
import cn.octopus.core.base.BaseApplication;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Api service
 * Created by JieGuo on 2017/3/16.
 */

public abstract class Api<T> {
    private static final String PLATFORM = "android";
    private static final String CLIENT = "octopus-mvp-client";

    private T apiService;

    protected Api() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        httpClientBuilder.networkInterceptors()
                .add(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder req = chain.request().newBuilder();
                        req.addHeader("Accept-Charset", "utf-8");
                        req.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("x-version", BuildConfig.VERSION_NAME)
                                .addHeader("x-platform", PLATFORM)
                                .addHeader("x-client", CLIENT);

                        addAuthorizationHeader(req);
                        Response response = chain.proceed(req.build());
                        return response.newBuilder().build();
                    }
                });
        httpClientBuilder.addInterceptor(
                new HttpLoggingInterceptor().setLevel(
                        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE
                )
        );


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        GsonBuilder gsonBuilder = new GsonBuilder();

        Retrofit retrofit = retrofitBuilder
                .baseUrl(BaseApplication.getInstance().getBaseUrl())
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();

        apiService = createApiService(retrofit);
    }

    protected abstract T createApiService(Retrofit retrofit);

    /**
     * add token here.
     * @param req request
     */
    protected void addAuthorizationHeader(Request.Builder req) {
//        if (!TextUtils.isEmpty(FastData.getToken())) {
//            String authorizationHeader = String.format(Locale.CHINA, "Bearer %s", FastData.getToken());
//            req.addHeader("Authorization", authorizationHeader);
//        }
    }

    public T getService() {
        return apiService;
    }

}
