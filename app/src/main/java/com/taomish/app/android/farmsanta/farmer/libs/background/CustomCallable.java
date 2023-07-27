package com.taomish.app.android.farmsanta.farmer.libs.background;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

@SuppressWarnings("rawtypes")
public abstract class CustomCallable implements Callable {

    private WeakReference<CustomThreadPoolManager> mCustomThreadPoolManagerWeakReference;

    public WeakReference<CustomThreadPoolManager> getCustomThreadPoolManagerWeakReference() {
        return mCustomThreadPoolManagerWeakReference;
    }

    public void setCustomThreadPoolManager(CustomThreadPoolManager customThreadPoolManager) {
        this.mCustomThreadPoolManagerWeakReference = new WeakReference<>(customThreadPoolManager);
    }
}
