package demo.octopus.cn.core.ui

import android.os.Bundle
import android.util.Log
import cn.octopus.core.base.BasePresenterActivity
import cn.octopus.core.utils.ToastUtil
import core.octopus.cn.demo.R
import demo.octopus.cn.core.mvp.presentaion.UserPresentation
import demo.octopus.cn.core.mvp.presenter.UserPresenter

class LoginActivity : BasePresenterActivity(), UserPresentation.View {

    val userPresenter = UserPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login() {
        userPresenter.login("jerry", "", "", { response ->
            if (response.isSuccess) {
                ToastUtil.show("sccuess")
            } else {
                ToastUtil.show("error")
            }
        }, {
            Log.e(TAG, "error", it)
        })
    }

    fun changePwd() {
        userPresenter.changePwd("", "", "", {
            response ->
            when (response.isSuccess) {
                true -> onLoginSuccess()
                else -> loginFail()
            }
        }, { Log.e(TAG, "error", it) })
    }

    override fun onLoginSuccess() {

    }

    fun loginFail() {

    }

}
