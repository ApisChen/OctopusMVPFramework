package demo.octopus.cn.core;

import cn.octopus.core.base.BaseApplication;

/**
 * Created by JieGuo on 2017/8/2.
 */

public class DemoApplication extends BaseApplication {

    @Override
    public String getBaseUrl() {
        return "http://localhost:80/api/";
    }
}
