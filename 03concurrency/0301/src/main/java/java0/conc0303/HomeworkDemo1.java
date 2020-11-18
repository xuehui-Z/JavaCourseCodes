package java0.conc0303;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class HomeworkDemo1 {
    public static void main(String[] args) {

//        long start = System.currentTimeMillis();
//        // 在这里创建一个线程或线程池，
//        // 异步执行 下面方法
//
//        int result = sum(); //这是得到的返回值
//
//        // 确保  拿到result 并输出
//        System.out.println("异步计算结果为：" + result);
//
//        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
//
//        // 然后退出main线程
        run(bySleep, "bySleep");

        run(byJoin, "byJoin");

        run(byCAS, "byCAS");

        run(byLock, "byLock");

        run(byObjectNotify, "byObjectNotify");

        run(byCountDownLatch, "byCountDownLatch");

        run(byFuture, "byFuture");

        run(byFutureTask, "byFutureTask");

        run(byCompletableFuture, "byCompletableFuture");
    }

    public static void run(Supplier<Integer> supplier, String type) {
        System.out.println(type);
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        // 这是得到的返回值
        int result = supplier.get();

        // 确保  拿到result 并输出
        System.out.print("异步计算结果为：" + result + " ");

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    private static final Supplier<Integer> bySleep = () -> {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        AtomicInteger result = new AtomicInteger();
        executorService.execute(() -> result.set(sum()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return result.get();
    };

    private static final Supplier<Integer> byJoin = () -> {
        AtomicInteger result = new AtomicInteger();
        Thread thread = new Thread(() -> result.set(sum()));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result.get();
    };

    public static final Supplier<Integer> byCAS = () -> {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        AtomicBoolean isDone = new AtomicBoolean(false);
        AtomicInteger result = new AtomicInteger();
        executorService.execute(() -> {
            result.set(sum());
            isDone.set(true);
        });
        while (!isDone.get()) {
        }
        executorService.shutdown();
        return result.get();
    };

    public static final Supplier<Integer> byLock = () -> {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AtomicInteger result = new AtomicInteger();
        AtomicBoolean isDone = new AtomicBoolean(false);
        executorService.execute(() -> {
            lock.lock();
            try {
                result.set(sum());
                isDone.set(true);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        });

        lock.lock();
        try {
            while (!isDone.get()) {
                condition.await();
            }
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        executorService.shutdown();
        return result.get();
    };

    public static final Supplier<Integer> byObjectNotify = () -> {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Object object = new Object();
        AtomicInteger result = new AtomicInteger();
        AtomicBoolean isDone = new AtomicBoolean(false);
        executorService.execute(() -> {
            synchronized (object) {
                result.set(sum());
                isDone.set(true);
                object.notifyAll();
            }
        });

        synchronized (object) {
            while (!isDone.get()) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        executorService.shutdown();
        return result.get();
    };

    public static final Supplier<Integer> byCountDownLatch = () -> {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicInteger result = new AtomicInteger();
        executorService.execute(() -> {
            result.set(sum());
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return result.get();
    };

    private static final Supplier<Integer> byFuture = () -> {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(()->sum());
        Integer result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return result;
    };

    private static final Supplier<Integer> byFutureTask = () -> {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<Integer> futureTask = new FutureTask<>(Homework03::sum);
        executorService.submit(futureTask);
        Integer result = null;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return result;
    };

    public static final Supplier<Integer> byCompletableFuture = () -> {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(Homework03::sum);
        return completableFuture.join();
    };
}
