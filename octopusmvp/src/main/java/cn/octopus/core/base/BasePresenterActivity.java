package cn.octopus.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.Queue;

import cn.octopus.core.events.EventBusDelegate;
import cn.octopus.core.events.IEventBus;
import cn.octopus.core.events.NoneEvent;
import io.reactivex.disposables.Disposable;

/**
 * base presenter activity
 *
 * Created by JieGuo on 2017/7/17.
 */

public abstract class BasePresenterActivity extends AppCompatActivity implements BasePresenterView {

    protected final String TAG = this.getClass().getSimpleName();

    private Queue<Disposable> disposableQueue;
    public TextView right;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposableQueue = new LinkedList<>();
        if (this instanceof IEventBus) {
            EventBusDelegate.register(this);
        }
    }

    @Override
    public AppCompatActivity getCurrentActivity() {
        return this;
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposableQueue.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if (disposableQueue != null && disposableQueue.size() > 0) {
            while (disposableQueue.size() > 0) {
                try {
                    disposableQueue.poll().dispose();
                } catch (Throwable e) {
                    Log.e(TAG, "error", e);
                }
            }
        }
        super.onDestroy();
        if (this instanceof IEventBus) {
            EventBusDelegate.unregister(this);
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onEvent(NoneEvent object) {

    }
}
