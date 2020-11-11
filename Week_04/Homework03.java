package java0.conc0303;

import javax.xml.transform.Result;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
    
    public static void main(String[] args) throws Exception {
        
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        //int result = getAsyncResult1(); //这是得到的返回值
        //int result = getAsyncResult2();
        //int result = getAsyncResult3();
        //int result = getAsyncResult4();
        //int result = getAsyncResult5();
        //int result = getAsyncResult6();
        int result = getAsyncResult7();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
         
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        
        // 然后退出main线程
    }

    // 提交任务到线程池，再通过Future.get方法获取执行结果
    private static int getAsyncResult1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(Homework03::sum);
        executorService.shutdown();
        return future.get();
    }

    // 在子线程中执行FutureTask包装的任务，再通过FutureTask.get方法获取执行结果
    private static int getAsyncResult2() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(Homework03::sum);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    // 使用信号量工具类同步主线程与子线程，使主线程等待子线程执行完才结束，执行结果通过共享内存同步
    private static int getAsyncResult3() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        DataObject dataObj = new DataObject();
        new Thread(() -> {
            int sum = sum();
            dataObj.setData(sum);
            semaphore.release();
        }).start();
        semaphore.acquire();

        return dataObj.getData();
    }

    // 使用计数为1的CountDownLatch, 同步主线程与子线程，使主线程等待子线程执行完才结束，执行结果通过共享内存同步
    private static int getAsyncResult4() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        DataObject dataObj = new DataObject();
        new Thread(() -> {
            int sum = sum();
            dataObj.setData(sum);
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();

        return dataObj.getData();
    }

    // 使用可重入锁和Condition, 在子线程锁定和计算结果，并通知主线程解锁和获取数据
    private static int getAsyncResult5() throws InterruptedException {
        DataObject dataObj = new DataObject();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();
                int sum = sum();
                dataObj.setData(sum);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }).start();

        try {
            lock.lock();
            condition.await();
            return dataObj.getData();
        } finally {
            lock.unlock();
        }
    }

    // 使用CyclicBarrier, 同步主线程与子线程，使主线程等待子线程执行完才结束，执行结果通过共享内存同步
    private static int getAsyncResult6() throws InterruptedException, BrokenBarrierException {
        DataObject dataObj = new DataObject();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(() -> {
            int sum = sum();
            dataObj.setData(sum);
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        cyclicBarrier.await();
        return dataObj.getData();
    }

    // 使用synchronized同步子线程的写操作和主线程的读操作
    private static int getAsyncResult7() throws InterruptedException {
        DataObject dataObj = new DataObject();
        new Thread(() -> {
            synchronized (dataObj) {
                int sum = sum();
                dataObj.setData(sum);
                dataObj.notifyAll();
            }
        }).start();

        synchronized (dataObj) {
            dataObj.wait();
            return dataObj.getData();
        }
    }
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    private static class DataObject {
        private int data;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }
}
