package demo.octopus.cn.core.mvp.presentaion;

import cn.octopus.core.base.BasePresenterView;
import demo.octopus.cn.core.service.responses.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * simple user presentation
 *
 * Created by JieGuo on 2017/8/2.
 */

public interface UserPresentation {

    /**
     * 你可以通过这样的方式来定义 user model 所具备的能力范围，但是也可以不定义接口。无论哪种方式，怎么轻松怎么来。
     */
    interface Model {
        Observable<BaseResponse<String>> login(
                String userName, String pwd, String encType, String verifyCode);
    }

    interface Presenter {

        void login(
                String username, String pwd, String verifyCode,
                Consumer<BaseResponse<String>> onLoad, Consumer<Throwable> onError);

        void changePwd(String username, String oldPwd, String currentPwd,
                       Consumer<BaseResponse<String>> onLoad, Consumer<Throwable> onError);
    }

    interface View extends BasePresenterView {
        void onLoginSuccess();
    }
}
