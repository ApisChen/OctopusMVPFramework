package cn.octopus.core.base;

import android.support.multidex.MultiDexApplication;
import cn.octopus.core.service.Api;
import java.util.HashMap;
import java.util.Map;

/**
 * the base application of this framework please extends of this application.
 *
 * Created by JieGuo on 2017/8/2.
 */

public abstract class BaseApplication extends MultiDexApplication {

  private static BaseApplication INSTANCE;

  private Map<Class, Object> apiMap = new HashMap<>();

  public static BaseApplication getInstance() {
    return INSTANCE;
  }

  public abstract String getBaseUrl();

  @Override public void onCreate() {
    super.onCreate();

    INSTANCE = this;
  }

  /**
   * Get the retrofit service class by the xxApi class. If the class is initialized, just get
   * it from the cached instance; Or else, create a new instance via the newInstance() method.
   *
   * @param clazzOfApi xxxApi class which extend {@link Api }
   * @param <T> Type of retrofit service interface
   * @return the Retrofit service class object.
   */
  public <T extends Api> Object getRetrofitService(Class clazzOfApi) {
    T t = (T) (apiMap.get(clazzOfApi));
    if (t == null) {
      try {
        t = (T) clazzOfApi.newInstance();
        apiMap.put(clazzOfApi, t);
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    if (t == null) {
      throw new RuntimeException("can not get the Api class ");
    }
    return t.getService();
  }
}
