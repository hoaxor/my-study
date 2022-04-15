package com.hyh.jucutil;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义的线程池
 */
@Slf4j(topic = "thread pool test")
public class MyThreadPoolTest {
    public static void main(String[] args) {
        test3();
    }

    /**
     * 使用拒绝策略
     */
    public static void test3() {
        ThreadPool threadPool = new ThreadPool(1, 1, TimeUnit.SECONDS, 1, (queue, task) -> {
            // 等待
//             queue.put(task);
            // 带超时时间的等待
//            queue.offer(task, 1500, TimeUnit.MILLISECONDS);
            // 放弃执行任务
//            log.debug("放弃任务{}", task);
//            抛出异常
//            throw new RuntimeException("队列已满");
            // 让调用者自己执行
//             task.run();
        });
        for (int i = 0; i < 3; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", j);
            });
        }
    }

    /**
     * threadPool任务队列已满情况
     */
    public static void test2() {
        ThreadPool threadPool = new ThreadPool(2, 1, TimeUnit.SECONDS, 5, null);
        for (int i = 0; i < 15; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", j);
            });
        }
    }

    /**
     * threadPool任务队列未满情况
     */
    public static void test1() {
        ThreadPool threadPool = new ThreadPool(2, 1, TimeUnit.SECONDS, 10, null);
        for (int i = 0; i < 5; i++) {
            int j = i;
            threadPool.execute(() -> {
                log.debug("{}", j);
            });
        }
    }

}

@Slf4j(topic = "threadPool")
class ThreadPool {

    /**
     * 任务队列
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 线程集合
     */
    private final Set<Worker> workers;

    /**
     * 核心线程数
     */
    private final int coreSize;

    /**
     * 获取任务时的超时时间
     */
    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        workers = new HashSet<>();
        this.rejectPolicy = rejectPolicy;
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        // 当任务数没有超过coreSize时，直接交给worker对象执行
        // 如果任务数超过coreSize时，加入队列暂存
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("{}加入工作线程集合", worker);
                workers.add(worker);
                worker.start();
            } else {
//                log.debug("task {} put in taskQueue", task);
                if (rejectPolicy == null) {
                    //队列满时会阻塞在此方法内
                    taskQueue.put(task);
                } else {
                    taskQueue.put(rejectPolicy, task);
                }

            }
        }

    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // 当task不为空，执行任务
            // 当task执行完后，接着再从任务队列中获取任务并执行
//            while (task != null || (task = taskQueue.take()) != null) {
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                log.debug("{}开始执行", task);
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }

            synchronized (workers) {
                log.debug("移除工作线程{}", this);
                workers.remove(this);
            }
        }


    }
}


@Slf4j(topic = "blockingQueue")
class BlockingQueue<T> {

    /**
     * 任务队列
     */
    private Deque<T> queue = new ArrayDeque<>();

    /**
     * 锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 生产者条件变量
     */
    private Condition fullWaitSet = lock.newCondition();

    /**
     * 消费者条件变量
     */
    private Condition emptyWaitSet = lock.newCondition();

    /**
     * 容量
     */
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        long nanos = unit.toNanos(timeout);
        try {
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    //emptyWaitSet.awaitNanos返回值为 剩余等待时间
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public void put(T t) {
        lock.lock();
        try {
            while (queue.size() >= capacity) {
                try {
                    log.debug("{}等待进入任务队列", t);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("{}加入任务队列", t);
            queue.addLast(t);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    public void put(RejectPolicy<T> rejectPolicy, T t) {
        lock.lock();
        try {
            //队列已满 执行拒绝策略
            if (queue.size() >= capacity) {
                log.debug("任务队列已满，执行拒绝策略");
                rejectPolicy.reject(this, t);
            } else {
                log.debug("{}加入任务队列", t);
                queue.addLast(t);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void offer(T t, long timeout, TimeUnit unit) {
        lock.lock();
        long nanos = unit.toNanos(timeout);
        try {
            while (queue.size() >= capacity) {
                try {
                    if (nanos <= 0) {
                        log.debug("{}等待超时，放弃等待", t);
                        return;
                    }
                    log.debug("{}等待进入任务队列", t);
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("{}加入任务队列", t);
            queue.addLast(t);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}

@FunctionalInterface
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T t);
}