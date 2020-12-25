package com.pency.ch1;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author Pency
 * @Description 新启线程的三种方式演示 ：继承Thread类、 实现Runnable或者Callable接口。
 * 特点:最终启动线程 最终直接方式都是Thread.start   Runnable或Callable最终也是包装成了Thread
 *
 *
 * 2、演示线程安全中断
 **/
public class NewThread {
    private static int count = 5;


    private static class UseThread extends Thread {
        @Override
        public void run() {
            int i = 1;
            while (true) {
                try {
                    // sleep/wait/yield/join 等阻塞方法 接收到interrupt信号会抛出InterruptedException异常 并消除中断信息
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    System.out.println("************* catch InterruptedException....");
                    //所以对于sleep等方法 如果想让中断信息起作用 需要在InterruptedException 捕获处理里 再次调用interrupt()
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":extends Thread run...." + i++);
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("中断标志位："+isInterrupted());
                    //中断信号 不会直接中断线程 需要看业务代码流程控制
                    break;
                }
                System.out.println("中断标志位："+isInterrupted());
            }
        }
    }

    private static class UseRunnable implements Runnable {
        @Override
        public void run() {
            int i = 1;
            while (i <= count) {
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":implements Runnable run...." + i++);

            }
        }
    }

    private static class UseCallable implements Callable<String> {
        @Override
        public String call() {
            int i = 1;
            while (i <= count) {
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":implements Runnable run...." + i++);
            }
            return "UseCallable return value";
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        UseThread useThread = new UseThread();
        useThread.setName("Thread");
        useThread.start();

        useThread.interrupted();
        UseRunnable useRunnable = new UseRunnable();
        new Thread(useRunnable, "Thread-Runnable").start();

        UseCallable useCallable = new UseCallable();
        //Thread类没有接收Callable接口的构造方法 也就是无法直接包装成Thread 只有接收Runable接口的构造方法 所以需要将Callable接口实现类包装成Runable接口的实现类（FuthreTask类）
        //interface RunnableFuture<V> extends Runnable, Future<V>
        //public class FutureTask<V> implements RunnableFuture<V>
        //public FutureTask(Callable<V> callable)   构造器接收Callable
        FutureTask<String> stringFutureTask = new FutureTask<>(new UseCallable());
        new Thread(stringFutureTask, "Thread-Runnable-Callable").start();
        System.out.println("Callable thread return:" + stringFutureTask.get());

        /*UseThread useThread = new UseThread();
        useThread.start();*/
        NewThread newThread = new NewThread();
        newThread.test1();
    }

    @Test //todo 2、演示 中断线程
    public void test1() throws InterruptedException {
        UseThread useThread = new UseThread();
        useThread.start();
        Thread.sleep(2000);
        //置中断位
        useThread.interrupt();
        System.out.println(useThread.isInterrupted());
        Thread.sleep(Integer.MAX_VALUE);
    }
}
