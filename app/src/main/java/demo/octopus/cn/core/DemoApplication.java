package demo.octopus.cn.core;

import cn.octopus.core.base.BaseApplication;
import cn.octopus.core.service.Api;

/**
 * Created by JieGuo on 2017/8/2.
 */

public class DemoApplication extends BaseApplication {

  /*
   * Just for test purpose, two Api instance for the api server.
   */
  private static Api app1ServerApi; // api from server1.
  private static Api app2ServerApi; // api from server2.
  @Override public void onCreate() {
    super.onCreate();
    app1ServerApi = new Api("http://192.168.12.1:8000");
    app2ServerApi = new Api("http://192.168.11.1:8900");
  }

  public static Api getApp1ServerApi() {
    return app1ServerApi;
  }

  public static Api getApp2ServerApi() {
    return app2ServerApi;
  }
}
