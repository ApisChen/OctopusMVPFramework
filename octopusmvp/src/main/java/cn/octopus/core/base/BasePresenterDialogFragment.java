package cn.octopus.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.Queue;

import cn.octopus.core.events.EventBusDelegate;
import cn.octopus.core.events.IEventBus;
import cn.octopus.core.events.NoneEvent;
import io.reactivex.disposables.Disposable;

/**
 * base dialog fragment
 *
 * Created by JieGuo on 2017/4/14.
 */

public abstract class BasePresenterDialogFragment extends AppCompatDialogFragment implements BasePresenterView {

    protected final String TAG = this.getClass().getSimpleName();

    private Queue<Disposable> disposableQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposableQueue = new LinkedList<>();
        if (this instanceof IEventBus) {
            EventBusDelegate.register(this);
        }
    }

    @Override
    public AppCompatActivity getCurrentActivity() {
        if (getActivity() instanceof AppCompatActivity) {

            return ((AppCompatActivity) getActivity());
        } else if (getActivity() == null) {

            throw new RuntimeException("Activity not attached");
        }
        return (AppCompatActivity) getActivity();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (disposableQueue != null) {
            disposableQueue.add(disposable);
        }
    }

    @Override
    public void onDestroyView() {
        if (disposableQueue != null && disposableQueue.size() > 0) {
            while (disposableQueue.size() > 0) {
                try {
                    disposableQueue.poll().dispose();
                } catch (Throwable e) {
                    Log.e(TAG, "error", e);
                }
            }
        }
        super.onDestroyView();
        if (this instanceof IEventBus) {
            EventBusDelegate.unregister(this);
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onEvent(NoneEvent object) {

    }
}
