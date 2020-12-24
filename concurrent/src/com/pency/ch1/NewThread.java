package com.pency.ch1;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author Pency
 * @Description 新启线程的三种方式演示 ：继承Thread类、 实现Runnable或者Callable接口。
 * 特点:最终启动线程 最终直接方式都是Thread.start   Runnable或Callable最终也是包装成了Thread
 **/
public class NewThread {
    private static int count=5;


   private static class UseThread extends Thread{
        @Override
        public void run() {
            int i=1;
            while (i<=count){
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":extends Thread run...."+i++);
            }
        }
    }

    private static class UseRunnable implements Runnable{
        @Override
        public void run() {
            int i=1;
            while (i<=count){
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":implements Runnable run...."+i++);

            }
        }
    }

    private static  class UseCallable implements Callable<String>{
        @Override
        public String call(){
            int i=1;
            while (i<=count){
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":implements Runnable run...."+i++);
            }
            return "UseCallable return value";
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        UseThread useThread = new UseThread();
        useThread.setName("Thread");
        useThread.start();

        UseRunnable useRunnable = new UseRunnable();
        new Thread(useRunnable,"Thread-Runnable").start();

        UseCallable useCallable = new UseCallable();
        //Thread类没有接收Callable接口的构造方法 也就是无法直接包装成Thread 只有接收Runable接口的构造方法 所以需要将Callable接口实现类包装成Runable接口的实现类（FuthreTask类）
        //interface RunnableFuture<V> extends Runnable, Future<V>
        //public class FutureTask<V> implements RunnableFuture<V>
        //public FutureTask(Callable<V> callable)   构造器接收Callable
        FutureTask<String> stringFutureTask = new FutureTask<>(new UseCallable());
        new Thread(stringFutureTask,"Thread-Runnable-Callable").start();
        System.out.println("Callable thread return:"+stringFutureTask.get());

    }
}
