package com.aaa.io.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    private static final String TAG = "ThreadUtil";
    private final Handler mHandler;
    private final ThreadPoolExecutor mWorkThread1;

    private ThreadUtil() {
        mHandler = new Handler(Looper.getMainLooper());
        // 只有一个非核心线程，阻塞队列1，采用丢弃旧任务的拒绝策略
        mWorkThread1 = new ThreadPoolExecutor(0, 1, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    private static class Holder {
        private static ThreadUtil instance = new ThreadUtil();
    }

    public static ThreadUtil get() {
        return Holder.instance;
    }

    public static Handler getMainHandler() {
        return get().mHandler;
    }

    public static void mainHandlerPost(Runnable runnable) {
        get().mHandler.post(runnable);
    }

    public ThreadPoolExecutor getWorkThread1() {
        return get().mWorkThread1;
    }

    public static void workThread1Execute(Runnable runnable) {
        get().mWorkThread1.execute(runnable);
    }
}
