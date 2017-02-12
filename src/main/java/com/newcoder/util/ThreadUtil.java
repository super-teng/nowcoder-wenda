package com.newcoder.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Super腾 on 2017/2/12.
 */
public class ThreadUtil {

    public static void testExecutor(){
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(new MyThread());
        executor.submit(new MyThread());
        executor.submit(new MyThread());
        executor.submit(new MyThread());
        executor.submit(new MyThread());
    }
    public static void testExecutor2(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                for(int i= 0;i<100 ;i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "正在执行");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public static class MyThread implements Runnable{
        @Override
        public void run() {
            for(int i= 0;i<100 ;i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "正在执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args){
        testExecutor2();
    }
}