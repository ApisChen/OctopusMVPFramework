package demo.octopus.cn.core.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.octopus.core.base.BasePresenterActivity;
import cn.octopus.core.utils.ToastUtil;
import core.octopus.cn.demo.R;
import demo.octopus.cn.core.mvp.presentaion.UserPresentation;
import demo.octopus.cn.core.mvp.presenter.UserPresenter;
import demo.octopus.cn.core.service.responses.BaseResponse;
import io.reactivex.functions.Consumer;

public class MainActivity extends BasePresenterActivity implements UserPresentation.View {

    private UserPresentation.Presenter presenter = new UserPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testLogin();
    }

    public void testLogin() {
        presenter.login("jerry", "123456!!!", "AB12D", new Consumer<BaseResponse<String>>() {
            @Override
            public void accept(BaseResponse<String> stringBaseResponse) throws Exception {
                ToastUtil.show(stringBaseResponse.getMessage());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtil.show("login fail.");
                Log.e(TAG, "error", throwable);
            }
        });
    }

    public void testChangePwd() {
        presenter.changePwd("jerry", "", "", new Consumer<BaseResponse<String>>() {
            @Override
            public void accept(BaseResponse<String> stringBaseResponse) throws Exception {
                ToastUtil.show(stringBaseResponse.getMessage());
                if (stringBaseResponse.isSuccess()) {
                    // when success doing.
                } else {
                    // when change fail doing.
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                // show the log in console.
                Log.e(TAG, "error", throwable);
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
    }
}
