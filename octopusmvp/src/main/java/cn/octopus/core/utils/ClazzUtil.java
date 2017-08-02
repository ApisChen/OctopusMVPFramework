package cn.octopus.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by weibolei on 03/08/2017.
 */

public class ClazzUtil {

  /**
   * Get the class from the clazz's generic type
   * @param clazz the specify class with specify generic type.
   * @return the class of generic type class.
   */
  public static Class getClassOfT(Class clazz) {

    Type genType = clazz.getGenericSuperclass();
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
    return (Class) params[0];
  }
}