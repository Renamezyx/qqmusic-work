package com.util;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorUtils {

    private final static ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*4);

    public static void execute(Runnable runnable){
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

}
