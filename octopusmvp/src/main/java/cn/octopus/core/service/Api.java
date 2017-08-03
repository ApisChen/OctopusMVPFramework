package cn.octopus.core.service;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.octopus.core.BuildConfig;
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
 * Api service.
 * Normally, this class will be initialized in app's Application only once. But, sometimes, if there
 * are more than one server for the app, you can initialized the Api class with specify host url
 * more than one.
 *
 * Created by JieGuo on 2017/3/16.
 */

public class Api {

  private Map<Class, Object> serviceMap = new HashMap<>();
  private static final String PLATFORM = "android";
  private static final String CLIENT = "octopus-mvp-client";
  private String hostUrl;
  private Retrofit retrofit;

  public Api(String hostUrl) {
    this.hostUrl = hostUrl;
    this.retrofit = getRetrofit();
  }

  /**
   * Get the Retrofit object base on the specified host url.
   *
   * @return Retrofit object.
   */
  private Retrofit getRetrofit() {
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
    httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
    httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

    httpClientBuilder.networkInterceptors().add(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
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
    httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE));

    Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

    GsonBuilder gsonBuilder = new GsonBuilder();

    Retrofit retrofit = retrofitBuilder.baseUrl(hostUrl)
        .client(httpClientBuilder.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        .build();

    return retrofit;
  }

  /**
   * add token here.
   *
   * @param req request
   */
  protected void addAuthorizationHeader(Request.Builder req) {
    //        if (!TextUtils.isEmpty(FastData.getToken())) {
    //            String authorizationHeader = String.format(Locale.CHINA, "Bearer %s", FastData.getToken());
    //            req.addHeader("Authorization", authorizationHeader);
    //        }
  }

  /**
   * Get the retrofit service class by the xxApi class. If the class is initialized, just get
   * it from the cached instance; Or else, create a new instance via the newInstance() method.
   *
   * @param clazzOfService retrofit service interface
   * @param <T> Type of retrofit service interface
   * @return the Retrofit service instance which created by retrofit.
   */
  public <T> T getRetrofitService(Class clazzOfService) {
    T t = (T) (serviceMap.get(clazzOfService));
    if (t == null) {
      t = (T) retrofit.create(clazzOfService);
      serviceMap.put(clazzOfService, retrofit.create(clazzOfService));
    }
    if (t == null) {
      throw new RuntimeException("can not get the Api class ");
    }
    return t;
  }
}
