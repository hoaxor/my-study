package com.hyh.jucutil.threadpool;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author : huang.yaohua
 * @date : 2022/4/17 21:28
 */
public class ForkJoinTest {

}

/**
 * RecursiveAction 无返回值
 * RecursiveTask 有返回值
 */
class MyTask<T> extends RecursiveTask<T> {

    private int n ;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected T compute() {
        return null;
    }
}
