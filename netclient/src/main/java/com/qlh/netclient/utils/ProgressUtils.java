package com.qlh.netclient.utils;

import android.app.Activity;
import com.qlh.netclient.dialog.DialogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.lang.ref.WeakReference;

/**
 * 作者：QLH on 2018-12-06
 * 描述：加载Loading
 */
public class ProgressUtils {

    public static <T> ObservableTransformer<T,T> applyProgressBar(
            @NonNull final Activity activity,String msg){
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final DialogUtils dialogUtils = new DialogUtils();
        dialogUtils.showProgress(activityWeakReference.get(),msg);
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Activity context;
                        if ((context = activityWeakReference.get())!=null
                                && !context.isFinishing()){
                            dialogUtils.dismissProgress();
                        }
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T,T> applyProgressBar(
            @Nullable final Activity activity){
        return applyProgressBar(activity,"");
    }
}
