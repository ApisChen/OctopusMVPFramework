package demo.octopus.cn.core.mvp.model;

import android.text.TextUtils;

import demo.octopus.cn.core.mvp.presentaion.UserPresentation;
import demo.octopus.cn.core.service.ApiService;
import demo.octopus.cn.core.service.DemoApi;
import demo.octopus.cn.core.service.responses.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.android.BuildConfig;
import io.reactivex.functions.Consumer;

/**
 * user model
 *
 * Created by JieGuo on 2017/8/2.
 */
public class UserModel implements UserPresentation.Model {

    private ApiService apiService = DemoApi.getInstance().getService();

    @Override
    public Observable<BaseResponse<String>> login(
            String userName, String pwd, String encType, String verifyCode) {

        return apiService.login(userName, pwd, encType, verifyCode);
    }

    public Observable<BaseResponse<String>> changePwd(
            String username, String oldPwd, String currentPwd) {

        if (TextUtils.isEmpty(username)) {
            return Observable.error(new Exception("用户名不能为空"));
        }
        if (TextUtils.isEmpty(oldPwd)) {
            return Observable.error(new Exception("旧密码不能空"));
        }

        if (TextUtils.isEmpty(currentPwd)) {
            return Observable.error(new Exception("当前密码不能为空"));
        }

        if (BuildConfig.DEBUG) {
            BaseResponse<String> changePwdResponse = new BaseResponse<>();
            changePwdResponse.setSuccess(true);
            changePwdResponse.setMessage("changed.");
            return Observable.just(changePwdResponse);
        }
        return apiService.changePwd(username, oldPwd, currentPwd)
                .doOnNext(new Consumer<BaseResponse<String>>() {
                    @Override
                    public void accept(BaseResponse<String> stringBaseResponse) throws Exception {
                        if (stringBaseResponse.isSuccess()) {
                            // 这里可以做存储或者其它的事情，总之model的作用就是在这一层做相关的事情。
                            // 或者说 这里可以做其它接口的缓存数据等等功能
                        } else {
                            // ignore or not.
                        }
                    }
                });
    }
}
