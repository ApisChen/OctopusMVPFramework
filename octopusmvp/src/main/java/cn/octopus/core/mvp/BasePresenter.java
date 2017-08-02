package cn.octopus.core.mvp;


import cn.octopus.core.base.BasePresenterView;
import io.reactivex.functions.Consumer;

/**
 * base presenter
 * Created by JieGuo on 2017/3/16.
 */

public abstract class BasePresenter {

    protected String TAG = this.getClass().getSimpleName();

    protected BasePresenterView view;

    public BasePresenter(BasePresenterView view) {
        this.view = view;
    }

}
