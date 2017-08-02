package demo.octopus.cn.core.mvp.presenter;

import cn.octopus.core.mvp.BasePresenter;
import demo.octopus.cn.core.mvp.model.UserModel;
import demo.octopus.cn.core.mvp.presentaion.UserPresentation;
import demo.octopus.cn.core.service.responses.BaseResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * user presenter
 * <p>
 * Created by JieGuo on 2017/8/2.
 */

public class UserPresenter extends BasePresenter implements UserPresentation.Presenter {

    private UserModel userModel;
    private UserPresentation.View userView;

    public UserPresenter(UserPresentation.View view) {
        super(view);
        userModel = new UserModel();
        userView = view;
    }

    @Override
    public void login(
            String username, String pwd, String verifyCode,
            Consumer<BaseResponse<String>> onLoad, Consumer<Throwable> onError) {
        String encType = "md5+password";

//        这里只是演示 不过这种操作不是很方便，但是不排除你需要这种操作。
//        但是大部分情况是不需要通过view的接口直接操作一个activity 我们不推荐这样使用。
//        Disposable disposable = userModel.login(username, pwd, encType, verifyCode)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<BaseResponse<String>>() {
//                    @Override
//                    public void accept(BaseResponse<String> stringBaseResponse) throws Exception {
//                        if (stringBaseResponse.isSuccess()) {
//                            userView.onLoginSuccess();
//                        }
//                    }
//                }, onError);
        Disposable disposable = userModel.login(username, pwd, encType, verifyCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onLoad, onError);
        view.addDisposable(disposable);
    }

    @Override
    public void changePwd(String username, String oldPwd, String currentPwd,
                          Consumer<BaseResponse<String>> onLoad, Consumer<Throwable> onError) {

        Disposable disposable = userModel.changePwd(username, oldPwd, currentPwd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onLoad, onError);
        view.addDisposable(disposable);
    }
}
