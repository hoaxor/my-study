

# JAVA并发编程



## 1. 并发编程概述

### 1.2 进程与线程

- 进程：进程是资源分配的最小单位
- 线程：线程是程序执行的最小单位，在Java中线程作为最小调度单位。

### 1.3并发与并行

- 在单核CPU下，线程是串行执行的，操作系统任务调度器将CPU时间片分给不同线程使用，由于CPU在线程间切换非常快，给人的感觉是同时运行的。总结：微观串行，宏观并行。一般将这种**线程轮流使用CPU**的做法成为并发（Concurrent），并发就是轮流处理多件事情。
- 在多核CPU环境下，每个核都可以调度运行线程，这时线程是并行（parallel）的，并行就是同时处理多件事情。



## 2. 基准测试工具

### [JMH](onenote:https://d.docs.live.net/172d03e57d1fe5fc/文档/hua 的笔记本/Java.one#JMH&section-id={F2760275-FDF3-45A4-A232-3B31EC62D17F}&page-id={8B70CD09-E2D5-416A-BCF5-051CC17BAB52}&end) ([Web 视图](https://1drv.ms/u/s!AvzlH33lAy0XhHpE94SelpF7zaWp?wd=target(Java.one|F2760275-FDF3-45A4-A232-3B31EC62D17F%2FJMH|8B70CD09-E2D5-416A-BCF5-051CC17BAB52%2F)))

JMH工程创建

```sh
mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DgroupId=com.hyh.jmh -DartifactId=jmh-test -Dversion=1.0.0-SNAPSHOT
```

JMH运行要将工程打成jar包

实例如下：

```sh
PS V:\ideaProject\jmh-test\jmh-test\target> java -jar .\benchmarks.jar
# VM invoker: E:\Program Files\Java\jre1.8.0_211\bin\java.exe
# VM options: <none>
# Warmup: 5 iterations, 1 s each
# Measurement: 3 iterations, 1 s each
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.hyh.jmh.MyBenchmark.testMethod

# Run progress: 0.00% complete, ETA 00:00:16
# Fork: 1 of 1
# Warmup Iteration   1: 22.670 ms/op
# Warmup Iteration   2: 21.348 ms/op
# Warmup Iteration   3: 20.976 ms/op
# Warmup Iteration   4: 22.897 ms/op
# Warmup Iteration   5: 18.817 ms/op
Iteration   1: 22.045 ms/op
Iteration   2: 21.455 ms/op
Iteration   3: 19.955 ms/op


Result: 21.152 ±(99.9%) 19.658 ms/op [Average]
  Statistics: (min, avg, max) = (19.955, 21.152, 22.045), stdev = 1.078
  Confidence interval (99.9%): [1.494, 40.810]


# VM invoker: E:\Program Files\Java\jre1.8.0_211\bin\java.exe
# VM options: <none>
# Warmup: 5 iterations, 1 s each
# Measurement: 3 iterations, 1 s each
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.hyh.jmh.MyBenchmark.testMethod2

# Run progress: 50.00% complete, ETA 00:00:11
# Fork: 1 of 1
# Warmup Iteration   1: 40.460 ms/op
# Warmup Iteration   2: 40.456 ms/op
# Warmup Iteration   3: 40.077 ms/op
# Warmup Iteration   4: 39.554 ms/op
# Warmup Iteration   5: 40.721 ms/op
Iteration   1: 40.549 ms/op
Iteration   2: 39.832 ms/op
Iteration   3: 39.589 ms/op


Result: 39.990 ±(99.9%) 9.106 ms/op [Average]


# Run complete. Total time: 00:00:22

Benchmark                        Mode  Samples   Score  Score error  Units
c.h.j.MyBenchmark.testMethod     avgt        3  21.152       19.658  ms/op
c.h.j.MyBenchmark.testMethod2    avgt        3  39.990        9.106  ms/op
PS V:\ideaProject\jmh-test\jmh-test\target> java -jar .\benchmarks.jar
# VM invoker: E:\Program Files\Java\jre1.8.0_211\bin\java.exe
# VM options: <none>
# Warmup: 5 iterations, 1 s each
# Measurement: 3 iterations, 1 s each
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.hyh.jmh.MyBenchmark.testMethod

# Run progress: 0.00% complete, ETA 00:00:16
# Fork: 1 of 1
# Warmup Iteration   1: 22.441 ms/op
# Warmup Iteration   2: 20.085 ms/op
# Warmup Iteration   3: 22.347 ms/op
# Warmup Iteration   4: 22.621 ms/op
# Warmup Iteration   5: 21.861 ms/op
Iteration   1: 24.546 ms/op
Iteration   2: 18.481 ms/op
Iteration   3: 23.756 ms/op


Result: 22.261 ±(99.9%) 60.154 ms/op [Average]
  Statistics: (min, avg, max) = (18.481, 22.261, 24.546), stdev = 3.297
  Confidence interval (99.9%): [-37.892, 82.415]


# VM invoker: E:\Program Files\Java\jre1.8.0_211\bin\java.exe
# VM options: <none>
# Warmup: 5 iterations, 1 s each
# Measurement: 3 iterations, 1 s each
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.hyh.jmh.MyBenchmark.testMethod2

# Run progress: 50.00% complete, ETA 00:00:10
# Fork: 1 of 1
# Warmup Iteration   1: 43.930 ms/op
# Warmup Iteration   2: 40.415 ms/op
# Warmup Iteration   3: 39.705 ms/op
# Warmup Iteration   4: 41.628 ms/op
# Warmup Iteration   5: 40.442 ms/op
Iteration   1: 40.181 ms/op
Iteration   2: 40.961 ms/op
Iteration   3: 40.427 ms/op


Result: 40.523 ±(99.9%) 7.275 ms/op [Average]
  Statistics: (min, avg, max) = (40.181, 40.523, 40.961), stdev = 0.399
  Confidence interval (99.9%): [33.248, 47.798]


# Run complete. Total time: 00:00:21

Benchmark                        Mode  Samples   Score  Score error  Units
c.h.j.MyBenchmark.testMethod     avgt        3  22.261       60.154  ms/op
c.h.j.MyBenchmark.testMethod2    avgt        3  40.523        7.275  ms/op

```





## 3. Java线程

### 3.1 创建和运行线程

- Thread

```java
    public static void newThread(){
        new Thread(){
            @Override
            public void run() {
                log.debug("override run");
            }
        }.start();
    }
```



- Runnable

```java
    public static void newRunnable() {
        new Thread(() -> log.debug("runnable"));
    }
```



- FutureTask

```java
    public static void newFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            log.debug("future task");
            return 1;
        });
        new Thread(futureTask).start();

        log.debug("{}", futureTask.get());
    }
```



### 3.2 线程运行原理

每个线程启动后，虚拟机就会为其分配一块栈内存，即Java虚拟机栈。

#### 3.2.1 线程上下文切换（Thread Context Switch）

因一些原因导致CPU不再执行当前线程，转而执行另一个线程的代码。

- 线程的cpu时间片用完
- 垃圾回收
- 有更高优先级的线程需要运行
- 线程调用了sleep、wait、join、park、synchronize、lock等方法

当上下文切换发生时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态，Java中对应的概念就是程序计数器（Program Counter Register），它的作用就是记住下一条jvm指令的执行地址，是线程私有的。

- 状态包括程序计数器、虚拟机栈每个栈帧的信息。
- 线程上下文切换频繁发生会影响性能。

### 3.3 start与run

```java
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("running....");
        }, "t1");
        log.debug("{}", t1.getState());
        //调用普通方法，线程不会启动，线程状态不变
        t1.run();
        log.debug("{}", t1.getState());
        //启动线程线程状态由NEW -> RUNNABLE
        t1.start();
        log.debug("{}", t1.getState());
        // 线程不能重复启动，重复启动会抛出IllegalThreadStateException
        t1.start();
    }
```



### 3.4 sleep与yield

#### sleep

1. 调用sleep会让当前线程从Running进入Timed Wating。
2. 其它线程可以使用`interrupt()`方法打断正在睡眠的线程，这时sleep方法会抛出`InterruptedException`
3. 睡眠结束后线程不一定会立即执行
4. sleep方法会使当前线程放弃CPU执行权
5. 建议使用`TimeUnit`的sleep代替Thread的sleep来获得更好的可读性。

```java
@Slf4j(topic = "join")
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            log.debug("before sleep");
            log.debug("t1 getState()={}", Thread.currentThread().getState());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage(), e);
                log.debug("before Thread.interrupted()");
                log.debug("t1 getState()={}", Thread.currentThread().getState());
                log.debug("t1.interrupted={}", Thread.interrupted());
                log.debug("after Thread.interrupted()");
                log.debug("t1 getState()={}", Thread.currentThread().getState());
            }
        }, "join");
        thread1.start();
        TimeUnit.SECONDS.sleep(2);
        if (thread1.getState() == Thread.State.TIMED_WAITING) {
            log.debug("before interrupt()");
            log.debug("t1.getState={}", thread1.getState());
            thread1.interrupt();
            log.debug("after interrupt()");
            //isInterrupted 不会清除线程中断状态
            log.debug("t1.isInterrupted={}", thread1.isInterrupted());
            log.debug("t1.getState={}", thread1.getState());
            log.debug("after isInterrupted()");
            log.debug("t1.getState={}", thread1.getState());
        }
    }
}
```



#### yield

1. 调用yield会让当前线程从running进入runnable，当前线程提示CPU调度器自己愿意让出CPU使用权，调度器可以忽略此提示。
2. 具体实现依赖于操作系统的任务调度器。

```java
@Slf4j(topic = "yield")
public class YieldTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            int i = 0;
            while (true) {
                log.debug("++++++++++++++++++++++++++i={}", ++i);
            }
        }, "t1");
        Thread thread2 = new Thread(() -> {
            int i = 0;
            while (true) {
                //提示调度器，自己愿意让出CPU使用权
                Thread.yield();
                log.debug("--------------------------i={}", ++i);
            }
        }, "t2");
        thread1.start();
        thread2.start();
    }
}
```



### 3.5 线程优先级

- 线程优先级会提示（hint）调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它。
- 如果CPU比较忙，那么高优先级的线程会获得更多的时间片，但CPU闲时，优先级几乎没有作用。

### 3.6 join方法详解

等待这个线程死亡

```java
    public static void test2() {
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.debug(e.getLocalizedMessage(), e);
            }
            i = 10;
        }, "t1");
        thread1.start();
        //i 不一定是 t1线程执行后的结果
        log.debug("i={}", i);
    }
```

```java
    public static void test1() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.debug(e.getLocalizedMessage(), e);
            }
            i = 10;
        }, "t1");
        thread1.start();
        //主线程等待t1线程执行结束
        thread1.join();
        //最终结果是10
        log.debug("i={}", i);
    }
```

### 3.7 interrupt方法详解

#### 打断sleep、wait和join的线程。

打断sleep、wait、join会清空打断状态

```java
    public static void interruptSleep() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.debug("after interrupt t1.state={}", Thread.currentThread().getState());//RUNNABLE
                log.debug("after interrupt t1.isInterrupted={}", Thread.currentThread().isInterrupted());//false
                log.error(e.getMessage(), e);
            }
        }, "t1");
        t1.start();
        SECONDS.sleep(1);
        log.debug("t1 sleep t1.state={}", t1.getState());//TIMED_WAITING
        t1.interrupt();
    }

    static final Object lock = new Object();

    public static void interruptWait() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                    log.debug("t1.state={}", Thread.currentThread().getState());//RUNNABLE
                    log.debug("t1.isInterrupted={}", Thread.currentThread().isInterrupted());//false
                }
            }

        }, "t1");
        t1.start();
        SECONDS.sleep(1);
        log.debug("t1 wait t1.state={}", t1.getState());//WAITING
        t1.interrupt();
        log.debug("after interrupt t1.state={}", t1.getState());//WAITING or RUNNABLE
        log.debug("after interrupt t1.isInterrupted={}", t1.isInterrupted());//false

    }

    public static void interruptJoin() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }, "t1");
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                log.debug("after interrupt t2.isInterrupted={}", Thread.currentThread().isInterrupted());//false
            }
        }, "t2");
        t2.start();
        SECONDS.sleep(1);
        t2.interrupt();
        log.debug("after interrupt t1.isInterrupted={}", t2.isInterrupted());//false
    }

```

#### 打断普通线程，不会清楚打断标记

```java
    public static void interruptCommon() throws InterruptedException {
        Thread common = new Thread(() -> {
            for (; ; ) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("common interrupted,common thread exit");
                    return;
                }
            }
        }, "common");

        common.start();
        SECONDS.sleep(1);
        common.interrupt();
        log.debug("after interrupt common.isInterrupted={}", common.isInterrupted());//true
    }

    public static void interruptCommon2() throws InterruptedException {
        Thread common = new Thread(() -> {
            for (; ; ) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("common interrupted,common sleep");
                    try {
                        //被标记为打断状态的线程进入sleep、wait、join状态，会被直接打断，并清除打断标记
                        SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                        log.debug("Thread.currentThread().isInterrupted()={}", Thread.currentThread().isInterrupted());//false
                        return;
                    }
                }
            }
        }, "common");

        common.start();
        SECONDS.sleep(1);
        common.interrupt();
        log.debug("after interrupt common.isInterrupted={}", common.isInterrupted());//true
    }
```

#### 打断park线程

```java
    public static void interruptPark() throws InterruptedException {
        Thread park = new Thread(() -> {
            LockSupport.park();
            log.debug("after interrupt currentThread.isInterrupted={}", Thread.currentThread().isInterrupted());//true
            //打断标记为true时，park方法不起作用
            //需要使用Thread.interrupted()清除打断标记
            log.debug("after interrupt Thread.interrupted()={}", Thread.interrupted());//true
            LockSupport.park();
            log.debug("unpark");
        }, "park");

        park.start();
        SECONDS.sleep(1);
        park.interrupt();
    }

```



#### interrupt应用

两阶段终止模式

![](\picture\两阶段终止模式.png)

### 3.8 守护线程和非守护线程

#### 守护线程

守护线程在所有非守护线程结束后强制结束。

- 垃圾回收器就是守护线程
- Tomcat中的Acceptor和Poller都是守护线程，所以Tomcat收到shutdown指令后，不会等待他们处理完当前请求。

```java
    public static void main(String[] args) {
        Thread daemon = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("daemon exit");
        }, "daemon");
        daemon.setDaemon(true);
        daemon.start();

        log.debug("main exit");
    }
```



### 3. 9 线程的状态

```java
   public enum State {
        /**
         * Thread state for a thread which has not yet started.
         */
        NEW,

        /**
         * Thread state for a runnable thread.  A thread in the runnable
         * state is executing in the Java virtual machine but it may
         * be waiting for other resources from the operating system
         * such as processor.
         */
        RUNNABLE,

        /**
         * Thread state for a thread blocked waiting for a monitor lock.
         * A thread in the blocked state is waiting for a monitor lock
         * to enter a synchronized block/method or
         * reenter a synchronized block/method after calling
         * {@link Object#wait() Object.wait}.
         */
        BLOCKED,

        /**
         * Thread state for a waiting thread.
         * A thread is in the waiting state due to calling one of the
         * following methods:
         * <ul>
         *   <li>{@link Object#wait() Object.wait} with no timeout</li>
         *   <li>{@link #join() Thread.join} with no timeout</li>
         *   <li>{@link LockSupport#park() LockSupport.park}</li>
         * </ul>
         *
         * <p>A thread in the waiting state is waiting for another thread to
         * perform a particular action.
         *
         * For example, a thread that has called <tt>Object.wait()</tt>
         * on an object is waiting for another thread to call
         * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
         * that object. A thread that has called <tt>Thread.join()</tt>
         * is waiting for a specified thread to terminate.
         */
        WAITING,

        /**
         * Thread state for a waiting thread with a specified waiting time.
         * A thread is in the timed waiting state due to calling one of
         * the following methods with a specified positive waiting time:
         * <ul>
         *   <li>{@link #sleep Thread.sleep}</li>
         *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
         *   <li>{@link #join(long) Thread.join} with timeout</li>
         *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
         *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
         * </ul>
         */
        TIMED_WAITING,

        /**
         * Thread state for a terminated thread.
         * The thread has completed execution.
         */
        TERMINATED;
    }
```



![](\picture\线程状态.jpg)

## 4. 并发之共享模型

###  4.1 共享问题

```java
    public static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        int l = 2000;
        Thread t1 = new Thread(() -> {
            int c = 0;
            while (c < l) {
                count++;
                c++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            int c = 0;
            while (c < l) {
                count--;
                c++;
            }
        }, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        log.debug("count={}", count);// 不一定是0
    }
```

#### 4.1.1 临界区Critical section

- 一段代码块内存在对共享资源的多线程读写操作，称这段代码为临界区

```java
    public static int count = 0;
    
    public static void increment(){
        //临界区
        count++;
    }
```

#### 4.1.2 竟态条件Race Condition

多个线程在临界区内执行，由于代码的执行序列不同而导致结果无法预测，称之发生了**竟态条件**。

### 4.2 synchronize

- 用对象锁保证了临界区代码的原子性，临界区内的代码对外是不可分割的，不会被线程切换所打断。

```java
        synchronized (SynchronizeTest.class) {
            count++;
        }
```

```java

    public static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        int l = 10000;
        Thread t1 = new Thread(() -> {
            int c = 0;
            while (c < l) {
                increment();
                c++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            int c = 0;
            while (c < l) {
                decrement();
                c++;
            }
        }, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        log.debug("count={}", count);//0
    }

    public static void increment() {
        synchronized (SynchronizeTest.class) {
            count++;
        }
    }

    public static void decrement() {
        synchronized (SynchronizeTest.class) {
            count--;
        }
    }
```



### 4.3 线程安全分析

### 4.4 Monitor（锁/管程）

#### 4.4.1 对象头

- 普通对象

```text
|--------------------------------------------------------------|
|                     Object Header (64 bits)                  |
|------------------------------------|-------------------------|
|        Mark Word (32 bits)         |    Klass Word (32 bits) |
|------------------------------------|-------------------------|
```



- 数组对象

```text
|---------------------------------------------------------------------------------|
|                                 Object Header (96 bits)                         |
|--------------------------------|-----------------------|------------------------|
|        Mark Word(32bits)       |    Klass Word(32bits) |  array length(32bits)  |
|--------------------------------|-----------------------|------------------------|
```



##### 4.4.1.1 Mark Word

https://www.cnblogs.com/makai/p/12466541.html

这部分主要用来存储对象自身的运行时数据，如`hashcode`、`gc分代年龄`等。`mark word`的位长度为JVM的一个Word大小，也就是说32位JVM的`Mark word`为32位，64位JVM为64位。

为了让一个字大小存储更多的信息，JVM将字的最低两个位设置为标记位，不同标记位下的Mark Word示意如下：

32位：

```text
|-------------------------------------------------------|--------------------|
|                  Mark Word (32 bits)                  |       State        |
|-------------------------------------------------------|--------------------|
| identity_hashcode:25 | age:4 | biased_lock:1 | lock:2 |       Normal       |
|-------------------------------------------------------|--------------------|
|  thread:23 | epoch:2 | age:4 | biased_lock:1 | lock:2 |       Biased       |
|-------------------------------------------------------|--------------------|
|               ptr_to_lock_record:30          | lock:2 | Lightweight Locked |
|-------------------------------------------------------|--------------------|
|               ptr_to_heavyweight_monitor:30  | lock:2 | Heavyweight Locked |
|-------------------------------------------------------|--------------------|
|                                              | lock:2 |    Marked for GC   |
|-------------------------------------------------------|--------------------|


```

64位：

```text
|------------------------------------------------------------------------------|--------------------|
|                                  Mark Word (64 bits)                         |       State        |
|------------------------------------------------------------------------------|--------------------|
| unused:25 | identity_hashcode:31 | unused:1 | age:4 | biased_lock:1 | lock:2 |       Normal       |
|------------------------------------------------------------------------------|--------------------|
| thread:54 |       epoch:2        | unused:1 | age:4 | biased_lock:1 | lock:2 |       Biased       |
|------------------------------------------------------------------------------|--------------------|
|                       ptr_to_lock_record:62                         | lock:2 | Lightweight Locked |
|------------------------------------------------------------------------------|--------------------|
|                     ptr_to_heavyweight_monitor:62                   | lock:2 | Heavyweight Locked |
|------------------------------------------------------------------------------|--------------------|
|                                                                     | lock:2 |    Marked for GC   |
|------------------------------------------------------------------------------|--------------------|

```



***lock***:2位的锁状态标记位，由于希望用尽可能少的二进制位表示尽可能多的信息，所以设置了lock标记。该标记的值不同，整个mark word表示的含义不同。

| biased_lock | lock | 状态   |
| ----------- | ---- | ---- |
| 0           | 01   | 无锁   |
| 1           | 01   | 偏向锁  |
| 0           | 00   | 轻量级锁 |
| 0           | 10   | 重量级锁 |
| 0           | 11   | GC标记 |

**biased_lock**：对象是否启用偏向锁，只占一位二进制，为1时表示启用偏向锁，为0时表示对象没有偏向锁。

**age**：4位的Java对象年龄，在GC中,默认情况下并行GC的年龄阈值为15，并发GC的年龄阈值为6。由于age只有4位，所以最大值为15，这就是`-XX:MaxTenuringThreshold`选项最大值为15的原因。

**identity_hashcode**：25位的对象哈希码，采用延迟加载技术。调用方法`System.identityHashCode()`计算，并会将结果写到该对象头中，当对象被锁定时，该值会移动到管程Monitor中去。

**thread**：持有偏向锁的线程ID。

**epoch**：偏向时间戳。

**ptr_to_lock_record**：指向栈中锁记录的指针。

**ptr_to_heavyweight_lock**：指向管程Monitor的指针。

##### 4.4.1.2 Klass Word/class pointer

这一部分用于存储对象的类型指针，该指针指向它的类元数据，JVM通过这个指针确定对象是哪个类的实例。该指针的位长度为JVM的一个字大小，即32位的JVM为32位，64位的JVM为64位。
如果应用的对象过多，使用64位的指针将浪费大量内存，统计而言，64位的JVM将会比32位的JVM多耗费50%的内存。为了节约内存可以使用选项`+UseCompressedOops`开启指针压缩，其中，oop即ordinary object pointer普通对象指针。开启该选项后，下列指针将压缩至32位：

1. 每个Class的属性指针（即静态变量）
2. 每个对象的属性指针（即对象变量）
3. 普通对象数组的每个元素指针

当然，也不是所有的指针都会压缩，一些特殊类型的指针JVM不会优化，比如指向PermGen的Class对象指针(JDK8中指向元空间的Class对象指针)、本地变量、堆栈元素、入参、返回值和NULL指针等。

##### 4.4.1.3 array length

如果对象是一个数组，那么对象头还需要有额外的空间用于存储数组的长度，这部分数据的长度也随着JVM架构的不同而不同：32位的JVM上，长度为32位；64位JVM则为64位。64位JVM如果开启`+UseCompressedOops`选项，**该区域长度也将由64位压缩至32位**。

#### 4.4.2 Monitor（Synchronize原理）

![](\picture\synchronize原理-monitor.jpg)

1. 刚开始`Monitor`中`Owner`为`null`。
2. 当某个线程`t1`执行到`Synchronize`语句就会将Monitor的所有者`Owner`置为`t1`，`Monitor`中只能有一个`Owner`。
3. 在`t1`上锁的过程中，如果其他线程也来执行`Synchronize`语句就会进入`Monitor`的`EntryList`，变成`BLOCKED`状态。
4. 当`t1`执行完同步代码块中的内容，然后唤醒`EntryList`中等待的线程来竞争锁，竞争时是非公平的。

> 不加Synchronize的对象不会关联监视器，不遵循以上规则

#### 4.4.3 偏向锁

轻量级锁在没有竞争时，每次重入任然需要执行CAS操作。

Java6中引入偏向锁来做进一步优化：只有第一次使用CAS将线程ID设置到对象的`mark word`，之后发现这个线程ID是自己就表示没有竞争，不需要重新进行`CAS`。以后只要不发生竞争，这个对象就归该线程所有。

##### 偏向状态

一个对象创建时：

- 如果开启了偏向锁（默认开启），那么对象创建后，`mark word`值为`0x05`即最后3位为`101`，这时它的thread、epoch、age都为0
- 偏向锁是默认是延迟的，不会在程序启动时立即生效，可以使用`-XX:BiasedLockingStartupDelay=0`来禁用延迟。
- 如果没有开启偏向锁，那么对象创建后，`mark word`值为`0x01`即最后3位`001`，这时它的hashcode、age都为0，第一次用到hashcode时才会赋值。
- 使用`-XX:-UseBiasedLocking`禁用偏向锁

```java
@Slf4j(topic = "biased")
public class BiasedTest {
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        // 偏向锁加载有延迟
        log.debug(ClassLayout.parseInstance(test).toPrintable());
        //调用hashcode方法会撤销对象的偏向状态
        //test.hashCode();
        synchronized (test){
            log.debug(ClassLayout.parseInstance(test).toPrintable());
        }
        // 偏向锁解锁后，线程ID仍记录在对象头中
        log.debug(ClassLayout.parseInstance(test).toPrintable());
    }
}

class Test {
}
```

##### 撤销-调用对象hashCode

调用了对象的hashCode，但偏向锁的对象中存储的是线程ID，如果调用hashCode会导致偏向锁被撤销。

- 轻量级锁会在锁记录中记录hashCode
- 重量级锁会在Monitor中记录hashCode

在调用hashCode后使用偏向锁，记得去掉`-XX:-UseBiasedLocking`

##### 撤销-其它线程使用对象

当有其他线程使用偏向锁对象时，会将偏向锁升级为轻量级锁。

```java
    public static void test2() {
        Test test = new Test();
        new Thread(() -> {
            log.debug(ClassLayout.parseInstance(test).toPrintable());
            synchronized (test) {
                log.debug(ClassLayout.parseInstance(test).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(test).toPrintable());
            log.debug("");
            synchronized (BiasedTest.class) {
                BiasedTest.class.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (BiasedTest.class) {
                try {
                    BiasedTest.class.wait();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
            
            log.debug(ClassLayout.parseInstance(test).toPrintable());
            synchronized (test) {
                //第二个线程获取对象锁，会撤销锁对象偏向状态升级为轻量级锁
                log.debug(ClassLayout.parseInstance(test).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(test).toPrintable());

        }, "t2").start();
    }
}

class Test {
}
```

##### 撤销-调用wait/notify



##### 批量重偏向

如果对象虽然被多个线程访问，但没有竞争，这时偏向了线程t1的对象仍有机会重新偏向t2，重偏向会重置对象的Thread Id。

当撤销偏向锁超过阈值（默认20）后，JVM会在给这些对象加锁时重新偏向至新的加锁线程，不会撤销锁对象偏向状态。



##### 批量撤销

当撤销偏向锁次数超过阈值（40）后，会将整个类中的所有对象都变为不可偏向的，新建的对象也是不可偏向的。



#### 4.4.4 轻量级锁

使用场景：如果一个对象虽然有多线程访问，但多线程访问的时间是错开的（也就是没有竞争），那么就会使用轻量级锁。

```java
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        synchronized (LightWeightLockTest.class) {
            test2();
        }
    }

    public static void test2() {
        synchronized (LightWeightLockTest.class) {
            System.out.println(2);
        }
    }
```

加锁步骤：

- 创建锁记录（Lock Record）对象，每个线程的栈帧包含一个锁记录的结构，内部可以存储锁定对象的Mark Word。

![](\picture\轻量级锁1.png)

- 让锁记录对象中`Object reference`指向锁对象，并尝试用`CAS`替换锁对象的`mark word`，将锁对象`mark word`的值存入锁记录

![](\picture\轻量级锁2.png)

- 如果`CAS`替换成功，对象头中存储了锁记录地址和状态00，表示由该线程给对象加锁。图示如下：

![](\picture\轻量级锁3.png)

- 如果`CAS`失败，有两种情况：

  - 如果是其它线程已经持有了该锁对象的轻量级锁，这时表明有竞争，进入锁膨胀过程。
  - 如果是自己执行了`synchronized`锁重入，那么再添加一个`Lock Record`作为重入的计数。

  ![](\picture\轻量级锁4.png)

- 当退出`synchronized`代码块，如果锁记录取值为`null`，表示有重入，这时重置锁记录。表示重入计数减一。

![](\picture\轻量级锁5.png)

- 当退出`synchronized`代码块，如果锁记录取值不为`null`，这时使用`CAS`将锁记录中存储的锁对象的`mark word`给锁对象还原。
  - 成功，则解锁成功。
  - 失败，说明轻量级锁进入了锁膨胀或以升级为重量级锁，进入重量级锁解锁流程。

##### 4.4.4.1 锁膨胀

如果在尝试加轻量级锁的过程中，`CAS`操作失败，这时一种情况就是有其它线程为此对象加上了轻量级锁（有竞争），这时需要进行锁膨胀。将轻量级锁变为重量级锁。

- 当`Thread-1`进行轻量级加锁时，`Thread-0`已经对该对象加了轻量级锁。

![](\picture\锁膨胀1.png)

- 这时`Thread-1`加轻量级锁失败，进入锁膨胀流程。

  - 为Object对象申请`Monitor`，让Object指向重量级锁地址。

  - 然后自己进入Monitor的`EntryList` `BLOCKED`

- 当`Thread-0`退出同步块解锁时，使用`CAS`将`mark word`的值恢复给对象头，失败。这时会进入重量级锁流程，即按照`Monitor`地址找到`Monitor`对象，设置`Owner`为`null`，唤醒`EntryList`中`BLOCKED`线程。



#### 4.4.5 重量级锁

##### 4.4.5.1 自旋优化

![](\picture\自旋优化1.png)

![](\picture\自旋优化2.png)

#### 4.4.6 锁消除

基于逃逸分析的锁消除，加锁对象不会逃逸出当前方法时，JIT会消除锁

### 4.5 wait/notify

##### 4.5.1 wait/notify原理

- Owner线程发现条件不满足，调用wait方法，即可进入`WaitSet`变为`WAITING`状态
- BLOCKED和WAITING的线程都处于阻塞状态，不占用CPU时间片
- BLOCKED线程会在Owner线程释放锁时唤醒。
- WAITING线程会在Owner线程调用notify或notifyAll时唤醒，但唤醒后并不意味着立刻获得锁，仍需进入EntryList重新竞争。

![](\picture\wait-notify.jpg)

##### 4.5.2 API介绍

- `wait()`让获取锁对象监视器的线程进入WaitSet等待，进入WATING状态。
- `notify()`在锁对象监视器的WaitSet中选一个线程唤醒
- `notifyAll()`在锁对象监视器的WaitSet中的线程都会被唤醒。
- 调用以上三个方法前需要先获取锁对象监视器。

#### 4.5.3 sleep与wait

- sleep不会释放锁，wait会释放锁
- 都会释放CPU使用权
- 线程状态都是TIMED_WAITING

#### 4.5.4 wait、notify的正确使用

```java
//线程A
synchronized(lock){
    //while 解决虚假唤醒的问题
    while(condition){
        lock.wait();
    }
    //do something
}

//线程B
synchronized(lock){
    lock.notifyAll();
}


```



### 4.6 Park/Unpark

#### 4.6.1 特点

- 与wait/notify/notifyAll相比，不必获取Monitor。
- park/unpark是**以线程为单位**来**阻塞**和**唤醒**线程，而notify只能随机唤醒一个等待中的线程，notifyAll是唤醒所有等待线程，没那么**精准**
- park/unpark可以先unpark，而wait/notify不能先notify

```java
@Slf4j(topic = "parkTest")
public class ParkTest {
    public static void main(String[] args) throws InterruptedException {
        Thread park = new Thread(() -> {
            log.debug("park thread start running");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("parking");
            //park后进入WAITING状态
            //若在park之前调用了unpark,当前线程不会阻塞
            LockSupport.park();
            log.debug("park is end, resume");
        }, "park");
        park.start();

        TimeUnit.SECONDS.sleep(2);

        LockSupport.unpark(park);
    }
}
```



#### 4.6.2 原理

每一个线程都有自己的Parker对象，由三部分组成，`_counter`，`_cond`和`_mutex`

##### 4.6.2.1 先park后unpark

![](\picture\park原理.png)

1. 当前线程调用`Unsafe.park()`方法
2. 检查`_counter`是否为零，这时获得`_mutex`互斥锁
3. 线程进入`_cond`条件变量阻塞
4. 设置`_counter = 0`

![](\picture\unpark原理.png)

1. 调用`Unsafe.unpark(Thread)`，设置`_counter`为`1` 
2. 唤醒`_cond`条件变量中的`Thread-0`
3. `Thread-0`恢复运行
4. 设置`_counter = 0`

##### 4.6.2.2 先unpark后park

![](\picture\upark后park.png)

1. 调用`Unsafe.unpark(Thread)`，设置`_counter = 1`
2. 其它线程调用`Unsafe.park()`
3. 检查`_counter`，本情况为1，这时线程无需阻塞，继续运行
4. 设置`_counter = 0`

### 4.7 多把锁

将锁的粒度细分

- 好处，可以增强并发度
- 坏处，如果一个线程需要同时获得多把锁，就容易发生死锁

#### 4.7.1 死锁

##### 4.7.1.1 示例

```java
@Slf4j(topic = "deadlock")
class DeadLockTest {
    public static void main(String[] args) throws Exception {
        final String a = "object1", b = "object2";
        new Thread(() -> {
            synchronized (a) {
                log.debug(a);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    log.debug(b);
                }
            }

        }).start();
        new Thread(() -> {
            synchronized (b) {
                log.debug(b);
                synchronized (a) {
                    log.debug(a);
                }
            }
        }).start();

    }
}
```



##### 4.7.1.2 定位死锁

jstack、jconsole

```sh
PS V:\ideaProject\juc-test> jstack -l 84780
2022-04-05 21:06:13
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.211-b12 mixed mode):

"logback-8" #21 daemon prio=5 os_prio=0 tid=0x000000001e513000 nid=0x14d48 waiting on condition [0x000000000121f000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:20
39)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"logback-7" #20 daemon prio=5 os_prio=0 tid=0x000000001e512000 nid=0x13618 waiting on condition [0x000000002002f000]     
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:20
39)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"logback-6" #19 daemon prio=5 os_prio=0 tid=0x000000001e462800 nid=0x1319c waiting on condition [0x000000001ff2e000]     
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:20
39)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"logback-5" #18 daemon prio=5 os_prio=0 tid=0x000000001e462000 nid=0x14778 waiting on condition [0x000000001fe2f000]     
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:20
39)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"logback-4" #17 daemon prio=5 os_prio=0 tid=0x000000001f298800 nid=0x1345c waiting on condition [0x000000001fd2e000]     
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:20
39)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"logback-3" #16 daemon prio=5 os_prio=0 tid=0x000000001f297800 nid=0x11088 waiting on condition [0x000000001fc2e000]     
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:20
39)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"logback-2" #15 daemon prio=5 os_prio=0 tid=0x000000001f286000 nid=0x13448 waiting on condition [0x000000001fb2e000]     
   java.lang.Thread.State: TIMED_WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.ja
va:2078)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1093) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"DestroyJavaVM" #14 prio=5 os_prio=0 tid=0x0000000003743800 nid=0x14974 waiting on condition [0x0000000000000000]        
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"Thread-1" #13 prio=5 os_prio=0 tid=0x000000001f285800 nid=0x13e84 waiting for monitor entry [0x000000001fa2e000]        
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.hyh.dealock.DeadLockTest.lambda$main$1(DeadLockTest.java:30)
        - waiting to lock <0x000000076c3b8c70> (a java.lang.String)
        - locked <0x000000076c3b8ca8> (a java.lang.String)
        at com.hyh.dealock.DeadLockTest$$Lambda$2/718231523.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"Thread-0" #12 prio=5 os_prio=0 tid=0x000000001f284800 nid=0x14a7c waiting for monitor entry [0x000000001f92f000]        
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.hyh.dealock.DeadLockTest.lambda$main$0(DeadLockTest.java:21)
        - waiting to lock <0x000000076c3b8ca8> (a java.lang.String)
        - locked <0x000000076c3b8c70> (a java.lang.String)
        at com.hyh.dealock.DeadLockTest$$Lambda$1/25126016.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"logback-1" #11 daemon prio=5 os_prio=0 tid=0x000000001f109800 nid=0xb078 waiting on condition [0x000000001f82e000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076bd4b948> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObj
ect)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:20
39)
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:1088) 
        at java.util.concurrent.ScheduledThreadPoolExecutor$DelayedWorkQueue.take(ScheduledThreadPoolExecutor.java:809)  
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None

"Service Thread" #10 daemon prio=9 os_prio=0 tid=0x000000001e383000 nid=0xbebc runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"C1 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x000000001e366800 nid=0x103fc waiting on condition [0x00000000000000
00]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x000000001e30f000 nid=0x14804 waiting on condition [0x00000000000000
00]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x000000001e2ed000 nid=0x11774 waiting on condition [0x00000000000000
00]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x000000001e2eb000 nid=0x1388c runnable [0x000000001e97e000]
   java.lang.Thread.State: RUNNABLE
        at java.net.SocketInputStream.socketRead0(Native Method)
        at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
        at java.net.SocketInputStream.read(SocketInputStream.java:171)
        at java.net.SocketInputStream.read(SocketInputStream.java:141)
        at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
        at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
        at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
        - locked <0x000000076b64fd28> (a java.io.InputStreamReader)
        at java.io.InputStreamReader.read(InputStreamReader.java:184)
        at java.io.BufferedReader.fill(BufferedReader.java:161)
        at java.io.BufferedReader.readLine(BufferedReader.java:324)
        - locked <0x000000076b64fd28> (a java.io.InputStreamReader)
        at java.io.BufferedReader.readLine(BufferedReader.java:389)
        at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:49)

   Locked ownable synchronizers:
        - None

"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000001d033800 nid=0x128a8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x000000001d079800 nid=0x14910 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x000000000383e000 nid=0x13914 in Object.wait() [0x000000000104e000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b388ed0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
        - locked <0x000000076b388ed0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)

   Locked ownable synchronizers:
        - None

"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000003835800 nid=0x148f8 in Object.wait() [0x000000001e27f000] 
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b386bf8> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x000000076b386bf8> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

   Locked ownable synchronizers:
        - None

"VM Thread" os_prio=2 tid=0x000000001cfe7800 nid=0x14a98 runnable

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000003759800 nid=0x14b94 runnable

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x000000000375b000 nid=0x14008 runnable

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x000000000375c800 nid=0x14964 runnable

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x000000000375e000 nid=0x11f88 runnable

"VM Periodic Task Thread" os_prio=2 tid=0x000000001e387800 nid=0x1433c waiting on condition

JNI global references: 317


Found one Java-level deadlock:
=============================
"Thread-1":
  waiting to lock monitor 0x000000000383b408 (object 0x000000076c3b8c70, a java.lang.String),
  which is held by "Thread-0"
"Thread-0":
  waiting to lock monitor 0x000000000383d718 (object 0x000000076c3b8ca8, a java.lang.String),
  which is held by "Thread-1"

Java stack information for the threads listed above:
===================================================
"Thread-1":
        at com.hyh.dealock.DeadLockTest.lambda$main$1(DeadLockTest.java:30)
        - waiting to lock <0x000000076c3b8c70> (a java.lang.String)
        - locked <0x000000076c3b8ca8> (a java.lang.String)
        at com.hyh.dealock.DeadLockTest$$Lambda$2/718231523.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)
"Thread-0":
        at com.hyh.dealock.DeadLockTest.lambda$main$0(DeadLockTest.java:21)
        - waiting to lock <0x000000076c3b8ca8> (a java.lang.String)
        - locked <0x000000076c3b8c70> (a java.lang.String)
        at com.hyh.dealock.DeadLockTest$$Lambda$1/25126016.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.

```



##### 4.7.1.3 哲学家就餐问题

有五位哲学家，围坐在圆桌旁

- 他们之作两件事情，思考和吃饭，思考一会吃口饭，吃完饭后接着思考
- 吃饭时要用两跟筷子吃，桌上共有五根筷子，每位哲学家左右手各有一根筷子
- 如果筷子被身边的人拿着，自己就得等待

```java
public class PhilosopherTest {
    public static void main(String[] args) {
        ChopStick chopStick1 = new ChopStick("1");
        ChopStick chopStick2 = new ChopStick("2");
        ChopStick chopStick3 = new ChopStick("3");
        ChopStick chopStick4 = new ChopStick("4");
        ChopStick chopStick5 = new ChopStick("5");
        new Thread(new Philosopher(chopStick1, chopStick2, "A")).start();
        new Thread(new Philosopher(chopStick2, chopStick3, "B")).start();
        new Thread(new Philosopher(chopStick3, chopStick4, "C")).start();
        new Thread(new Philosopher(chopStick4, chopStick5, "D")).start();
        new Thread(new Philosopher(chopStick5, chopStick1, "E")).start();
    }
}

class ChopStick {
    private final String name;

    public ChopStick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChopStick{" + name + '}';
    }
}

@Slf4j(topic = "philosopher")
class Philosopher implements Runnable {
    private final ChopStick left;
    private final ChopStick right;
    private final String name;

    public Philosopher(ChopStick left, ChopStick right, String name) {
        this.left = left;
        this.right = right;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (left) {
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    private void eat() {
        log.debug("{} eating", name);
        //thinking
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

以上实现会出现死锁情况

```sh
Found one Java-level deadlock:
=============================
"Thread-4":
  waiting to lock monitor 0x000000001ed55808 (object 0x000000076b4f3638, a com.hyh.dealock.ChopStick),
  which is held by "Thread-0"
"Thread-0":
  waiting to lock monitor 0x0000000003448928 (object 0x000000076b4f3678, a com.hyh.dealock.ChopStick),
  which is held by "Thread-1"
"Thread-1":
  waiting to lock monitor 0x0000000003449fd8 (object 0x000000076b4f36b8, a com.hyh.dealock.ChopStick),
  which is held by "Thread-2"
"Thread-2":
  waiting to lock monitor 0x000000001ed54208 (object 0x000000076b4f36f8, a com.hyh.dealock.ChopStick),
  which is held by "Thread-3"
"Thread-3":
  waiting to lock monitor 0x000000001ed54158 (object 0x000000076b4f3738, a com.hyh.dealock.ChopStick),
  which is held by "Thread-4"

Java stack information for the threads listed above:
===================================================
"Thread-4":
        at com.hyh.dealock.Philosopher.run(PhilosopherTest.java:56)
        - waiting to lock <0x000000076b4f3638> (a com.hyh.dealock.ChopStick)
        - locked <0x000000076b4f3738> (a com.hyh.dealock.ChopStick)
        at java.lang.Thread.run(Thread.java:748)
"Thread-0":
        at com.hyh.dealock.Philosopher.run(PhilosopherTest.java:56)
        - waiting to lock <0x000000076b4f3678> (a com.hyh.dealock.ChopStick)
        - locked <0x000000076b4f3638> (a com.hyh.dealock.ChopStick)
        at java.lang.Thread.run(Thread.java:748)
"Thread-1":
        at com.hyh.dealock.Philosopher.run(PhilosopherTest.java:56)
        - waiting to lock <0x000000076b4f36b8> (a com.hyh.dealock.ChopStick)
        - locked <0x000000076b4f3678> (a com.hyh.dealock.ChopStick)
        at java.lang.Thread.run(Thread.java:748)
"Thread-2":
        at com.hyh.dealock.Philosopher.run(PhilosopherTest.java:56)
        - waiting to lock <0x000000076b4f36f8> (a com.hyh.dealock.ChopStick)
        - locked <0x000000076b4f36b8> (a com.hyh.dealock.ChopStick)
        at java.lang.Thread.run(Thread.java:748)
"Thread-3":
        at com.hyh.dealock.Philosopher.run(PhilosopherTest.java:56)
        - waiting to lock <0x000000076b4f3738> (a com.hyh.dealock.ChopStick)
        - locked <0x000000076b4f36f8> (a com.hyh.dealock.ChopStick)
        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.

```



##### 4.7.1.4 活锁

两个线程互相改变对方的结束条件，最后谁也无法结束

```java
@Slf4j(topic = "liveLock")
public class LiveLockTest {
    static int count = 10;

    public static void main(String[] args) {
        new Thread(() -> {
            while (count <= 20) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("count={}", count++);
            }
        }, "increment").start();
        new Thread(() -> {
            while (count >= 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("count={}", count--);
            }
        }, "decrement").start();

    }
}
```



##### 4.7.1.5 饥饿

一个线程由于优先级太低，始终得不到CPU执行权，也不能够结束。

以下是线程饥饿的例子，使用顺序加锁的方式解决死锁问题

![](\picture\线程饥饿问题.png)

### 4.8 ReentrantLock

与`synchronized`相比具有如下特点

- 可中断
- 可设置超时时间
- 可设置为公平锁
- 支持多个条件变量

与`synchronized`一样，都支持可重入

#### 4.8.1 可重入

指同一个线程如果首次获得了这把锁，那么因为它是这把锁的拥有者，因此有权利再次获取这把锁，如果不可重入锁，那么第二次获取锁时，自己也会被挡住

```java
    public static void test2() throws InterruptedException {
        lock.lock();
        try {
            test21();
        } finally {
            lock.unlock();
        }
    }

    public static void test21() throws InterruptedException {
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
        } finally {
            lock.unlock();
        }
    }
```

#### 4.8.2 可打断

```java
    /**
     * 可打断特性
     */
    public static void test2() {
        Thread t1 = new Thread(() -> {
            try {
                // 没有竞争那么就会获取对象锁
                // 有竞争j就进入阻塞队列，可以被其它线程用interrupt打断
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            try {
                log.debug("{} get lock", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        t1.start();
        t1.interrupt();
    }
```

#### 4.8.3  超时时间

```java
    public static void test4() {
        Thread thread = new Thread(() -> {
//            boolean b = lock.tryLock();
            boolean b;
            try {
                b = lock.tryLock(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            if (b) {
                log.debug("获取成功");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                log.debug("获取失败");
            }
        });
        lock.lock();
        thread.start();
    }

```

##### 4.8.4 公平锁

ReentrantLock默认是不公平锁

```java
    public ReentrantLock() {
        sync = new NonfairSync();
    }

```

#### 4.8.5 条件变量

`synchronized`中也有条件变量，即`WaitSet`，当不满足条件时进入`WaitSet`等待

`ReentrantLock`的条件变量比`synchronized`的强大之处在于它支持多个条件变量，这就好比

- `synchronized`是那些不满足条件的线程都在一间休息室等待，
- `ReentrantLock`支持多见休息室，唤醒时也是按休息室来唤醒

使用流程

- await前需要获取锁
- await执行后，会释放锁，进入conditionObject等待
- await的线程被唤醒（打断或超时），重新竞争锁
- 竞争锁成功后继续执行

```java
@Slf4j(topic = "conditionTest")
public class ConditionTest {
    public static final ReentrantLock lock = new ReentrantLock();

    private static boolean hasCigarette = false;

    private static boolean hasTakeout = false;

    private static final Condition cigaretteSet = lock.newCondition();

    private static final Condition takeoutSet = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        getCigaretteThread().start();
        getTakeoutThread().start();

        TimeUnit.SECONDS.sleep(1);
        getSendCigaretteThread().start();

        TimeUnit.SECONDS.sleep(1);
        getSendTakeoutThread().start();

    }

    private static Thread getCigaretteThread() {
        return new Thread(() -> {
            while (!hasCigarette) {
                lock.lock();
                log.debug("没烟，休息一下");
                try {
                    cigaretteSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
            log.debug("有烟了，可以干活了！");
        }, "cigarette");
    }

    private static Thread getTakeoutThread() {
        return new Thread(() -> {
            while (!hasTakeout) {
                lock.lock();
                log.debug("没外卖，休息一下");
                try {
                    takeoutSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
            log.debug("有外卖了，可以干活了！");
        }, "takeout");
    }

    private static Thread getSendCigaretteThread() {
        return new Thread(() -> {
            lock.lock();
            try {
                log.debug("送烟的来了");
                hasCigarette = true;
                cigaretteSet.signal();
            } finally {
                lock.unlock();
            }
        }, "cigarette-sender");
    }

    private static Thread getSendTakeoutThread() {
        return new Thread(() -> {
            lock.lock();
            try {
                log.debug("送外卖的来了");
                hasTakeout = true;
                takeoutSet.signal();
            } finally {
                lock.unlock();
            }
        }, "takeout-sender");
    }

}
```



## 5. 共享模式之内存

### 5.1 Java内存模型

JMM即Java Memory Model

- 原子性-保证指令不会受线程上下文切换的影响
- 可见性-保证指令不会受CPU缓存影响
- 有序性-保证指令不会受CPU指令并行优化的影响



#### 5.1.1 可见性

指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。

```java
    public static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {

            }

        },"t").start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("set flag = false");
        //线程t不会如预想的停下来
        flag = false;

    }
```

以上结果的原因

JMM 定义了线程和主内存之间的抽象关系：

**线程之间的共享变量存储在主内存中，每个线程都有一个私有的本地内存，本地内存中存储了该线程以读/写共享变量的副本。**

JMM 的规定：
所有的共享变量都存储于主内存。这里所说的变量指的是实例变量和类变量，不包含局部变量，因为局部变量是线程私有的，因此不存在竞争问题。

每一个线程还存在自己的工作内存，线程的工作内存，保留了被线程使用的变量的工作副本。
线程对变量的所有的操作（读，取）都必须在工作内存中完成，而不能直接读写主内存中的变量。
不同线程之间也不能直接访问对方工作内存中的变量，线程间变量的值的传递需要通过主内存中转来完成。

![在这里插入图片描述](https://img-blog.csdnimg.cn/c693940a1d484bcd9badb3f916da0b27.png)

原文链接：https://blog.csdn.net/weixin_45476233/article/details/121558113

**可见性问题的解决方案：加锁和 使用 volatile 关键字**

1. 当一个线程进入 synchronizer 代码块后，线程获取到锁，会清空本地内存，然后从主内存中拷贝共享变量的最新值到本地内存作为副本，执行代码，又将修改后的副本值刷新到主内存中，最后线程释放锁。
2. 使用 volatile 修饰共享变量后，每个线程要操作变量时会从主内存中将变量拷贝到本地内存作为副本，当线程操作变量副本并写回主内存后，会通过 CPU 总线嗅探机制告知其他线程该变量副本已经失效，需要重新从主内存中读取。

#### 5.1.2 原子性

即一个操作或者多个操作 要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。

volatile不能保证原子性，**为保证原子性必须加锁或使用原子类型变量**

#### 5.1.3 有序性

##### 5.1.3.1  重排序

为了提高性能，在遵守 as-if-serial 语义（即不管怎么重排序，单线程下程序的执行结果不能被改变。编译器，runtime 和处理器都必须遵守。）的情况下，编译器和处理器常常会对指令做重排序。

一般重排序可以分为如下三种类型：

编译器优化重排序：编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序。
指令级并行重排序：现代处理器采用了指令级并行技术来将多条指令重叠执行。如果不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序。
内存系统重排序：由于处理器使用缓存和读 / 写缓冲区，这使得加载和存储操作看上去可能是在乱序执行。

**数据依赖性**：如果两个操作访问同一个变量，且这两个操作中有一个为写操作，此时这两个操作之间就存在数据依赖性。这里所说的数据依赖性仅针对单个处理器中执行的指令序列和单个线程中执行的操作，不同处理器之间和不同线程之间的数据依赖性不被编译器和处理器考虑。

从 Java 源代码到最终执行的指令序列，会分别经历下面三种重排序：
![在这里插入图片描述](https://img-blog.csdnimg.cn/fdc69dca22974e2ba31810dff30ad1d7.png)
原文链接：https://blog.csdn.net/weixin_45476233/article/details/121558113

```java
int a = 0;
// 线程 A
a = 1;           // 1
flag = true;     // 2

// 线程 B
if (flag) { // 3
  int i = a; // 4
}
```

单看上面的程序好像没有问题，最后 i 的值是 1。但是为了提高性能，编译器和处理器常常会在不改变数据依赖的情况下对指令做重排序。假设线程 A 在执行时被重排序成先执行代码 2，再执行代码 1；而线程 B 在线程 A 执行完代码 2 后，读取了 flag 变量。由于条件判断为真，线程 B 将读取变量 a。此时，变量 a 还根本没有被线程 A 写入，那么 i 最后的值是 0，导致执行结果不正确

使用 volatile 不仅保证了变量的内存可见性，还禁止了指令的重排序，即保证了 volatile 修饰的变量编译后的顺序与程序的执行顺序一样。那么使用 volatile 修饰 flag 变量后，在线程 A 中，保证了代码 1 的执行顺序一定在代码 2 之前。



### 5.2 习题

#### 线程安全单例

单例模式有很多实现方法，饿汉、懒汉、静态内部类、枚举类，试分析每种实现下获取单例对象时的线程安全问题，思考注释中的问题。

> 饿汉式：类加载就会创建单例对象
>
> 懒汉式：首次使用时创建单例对象

```java
/**
 * 1. 为什么加final 防止继承
 * 2. 实现了Serializable，如何防止反序列化
 *
 * @author : huang.yaohua
 * @date : 2022/4/10 19:50
 */
@Slf4j(topic = "singletonPractice")
public final class SingletonPractice implements Serializable {

    /**
     * 3. 为什么设置为私有，能否防止反射创建新的实例？
     */
    private SingletonPractice() {
    }

    /**
     * 4. 这样初始化能否保证对象创建时的线程安全？
     */
    private static final SingletonPractice INSTANCE = new SingletonPractice();

    /**
     * 5. 为什么提供静态方法而不是将INSTANCE设为public？
     */
    public static SingletonPractice getInstance() {
        return INSTANCE;
    }

    /**
     * 防止反序列化
     */
    public Object readResolve() {
        return INSTANCE;
    }
}
```



## 6. 共享模型之无锁

### 6.1 问题提出

```java
/**
 * 用不加锁的方式实现余额的正确管理
 *
 * @author : huang.yaohua
 * @date : 2022/4/10 20:50
 */
@Slf4j(topic = "nolock")
public class NoLockTest {
    public static void main(String[] args) {
        UnsafeAccount unsafeAccount = new UnsafeAccount(10000);
        SynchronizedAccount synchronizedAccount = new SynchronizedAccount(10000);
        CASAccount casAccount = new CASAccount(10000);
        Account.demo(unsafeAccount);

        Account.demo(synchronizedAccount);

        Account.demo(casAccount);
    }
}

interface Account {
    /**
     * 获取余额
     */
    Integer getBalance();

    /**
     * 取钱
     */
    void withdraw(Integer amount);

    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }

        long start = System.nanoTime();
        //启动每一个线程 每个线程 余额 - 10
        //若初始金额为 10000 那么最终输出0 才是正确的结果
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance() + " cost=" + (System.nanoTime() - start) / 1000_000 + "ms");
    }
}
```



线程不安全

```java
package com.hyh.cas;

/**
 * 存在线程安全问题
 *
 * @author : huang.yaohua
 * @date : 2022/4/10 21:01
 */
public class UnsafeAccount implements Account {

    private Integer balance;

    public UnsafeAccount(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(Integer amount) {
        balance -= amount;
    }
}

```

synchronized版本

```java
package com.hyh.cas;

/**
 * @author : huang.yaohua
 * @date : 2022/4/10 21:05
 */
public class SynchronizedAccount implements Account {
    private Integer balance;

    public SynchronizedAccount(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }
}

```

AtomicInteger版本

```java
package com.hyh.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : huang.yaohua
 * @date : 2022/4/10 21:08
 */
public class CASAccount implements Account {
    private AtomicInteger balance;

    public CASAccount(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
//        balance.addAndGet(amount);
        while (true) {
            int prev = balance.get();

            int next = prev - amount;

            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}

```



### 6.2 CAS与volatile

`Compare And Swap（Compare And Set）`即`CAS`。

`Java`中`CAS`的底层是`lock cmpxchg`指令（X86架构），在单核CPU和多核CPU下都能够保证**比较交换**的原子性

在多核情况下，某个核执行到带`lock`的指令时，CPU会让总线锁住，当这个核把比指令执行完毕，在开启总线。这个过程不会被线程的调度机制所打断，保证了多个线程对内存操作的准确性，是原子的。

`CAS`必须借助`volatile`才能读取到共享变量的最新值来实现**比较并交换**的效果

#### CAS特点

适用线程少，多核CPU场景

- CAS是基于乐观锁的思想，最乐观的估计，不怕别的线程来修改共享变量，失败重试。
- sychronized是基于悲观锁的思想。
- CAS体现的是无锁并发、无阻塞并发
  - 因为没有使用synchronized，所以线程不会陷入阻塞，这是提升效率的因素之一
  - 但是如果竞争激烈，重试必然频繁，效率反而会受影响



#### ABA问题

### 6.3 原子整数

```java
```



### 6.4 原子引用

```java
```

### 6.5 原子数组

```java
public class AtomicArrayTest {


    public static void main(String[] args) {
        demo(() -> new int[10],
                array -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array)));

        demo(() -> new AtomicIntegerArray(10),
                AtomicIntegerArray::length,
                AtomicIntegerArray::getAndIncrement,
                array -> System.out.println(array.toString()));
    }


    /**
     * 函数式接口
     * 1. supplier 无参有返回值
     * 2. function 一个参数有返回值  (x)->返回值； BiFunction: (x,y)->返回值；
     * 3. consumer 一个参数有返回值  (x)->void；  BiConsumer: (x,y)->void；
     *
     * @param arraySupplier  提供一个数组
     * @param lengthFunction 获取数组长度
     * @param putConsumer    操作数组元素，入参：数组和下标
     * @param printConsumer  操作数组元素，入参：数组
     * @param <T>            tt
     */

    public static <T> void demo(Supplier<T> arraySupplier,
                                Function<T, Integer> lengthFunction,
                                BiConsumer<T, Integer> putConsumer,
                                Consumer<T> printConsumer) {
        List<Thread> threadList = new ArrayList<>();

        T t = arraySupplier.get();

        Integer length = lengthFunction.apply(t);

        for (int i = 0; i < length; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(t, j % length);
                }
            }));
        }

        threadList.forEach(Thread::start);
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printConsumer.accept(t);
    }
}
```

### 6.6 字段更新器

```java
public class FieldUpdaterTest {
    public static void main(String[] args) throws InterruptedException {
        Student student = new Student();
        //
        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater.newUpdater(Student.class,
                String.class, "name");
        new Thread(() -> student.name = "1").start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(updater.compareAndSet(student, null, "hyh"));
        System.out.println(updater.get(student));
        System.out.println(student);

    }
}

class Student {
    //必须用volatile修饰
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

### 6.7 原子累加器

专用与数字累加，性能比原子整数更好

```java
public class AccumulatorTest {
    public static void main(String[] args) {
        demo(() -> new AtomicLong(0), AtomicLong::getAndIncrement);
        demo(LongAdder::new, LongAdder::increment);
    }

    /**
     * @param adderSupplier 提供累加器
     * @param action        执行累加操作
     */
    public static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T accumulator = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(accumulator);
                }
            }));
        }
        long start = System.nanoTime();
        //启动所有线程
        ts.forEach(Thread::start);
        //等待所有线程执行完毕
        ts.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(accumulator + " cost:" + (System.nanoTime() - start) / 1000_000 + "ms");
    }
}
```



### AtomicStampedReference

### AtomicMarkableReference

### 6.10 Unsafe

Unsafe提供了非常底层的，操作内存、线程的方法，Unsafe对象不能直接调用，只能通过反射获得

```java
/**
 * 通过Unsafe实现CAS操作
 */
@Slf4j(topic = "unsafe")
public class UnsafeTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        //Class com.hyh.cas.UnsafeTest can not access a member of class sun.misc.Unsafe with modifiers "private static final"
        theUnsafe.setAccessible(true);

        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        log.debug("unsafe={}", unsafe);

        //1. 获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();
        //2. 执行CAS操作
        log.debug("id cas = {}", unsafe.compareAndSwapInt(teacher, idOffset, 0, 1));
        log.debug("name cas = {}", unsafe.compareAndSwapObject(teacher, nameOffset, null, "hyh"));


    }
}

@Data
class Teacher {
    volatile String name;

    volatile int id;
}

```



## 7. 共享模型之不可变

### 7.1 日期转换问题

```java
@Slf4j(topic = "simpleDateFormat")
public class SimpleDateFormatTest {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    Date parse = simpleDateFormat.parse("2000-10-21");
                    log.debug("{}", parse);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```

`SimpleDateFormat`是线程不安全的，多线程环境下会出现异常

```text
2022-04-14 16:30:04.768 [Thread-52] DEBUG simpleDateFormat Line:21  - Sat Oct 21 00:00:00 CST 2000
Exception in thread "Thread-76" java.lang.NumberFormatException: For input string: ""
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Long.parseLong(Long.java:601)
	at java.lang.Long.parseLong(Long.java:631)
	at java.text.DigitList.getLong(DigitList.java:195)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2082)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:2162)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.hyh.jmm.immutable.SimpleDateFormatTest.lambda$main$0(SimpleDateFormatTest.java:20)
	at java.lang.Thread.run(Thread.java:750)
2022-04-14 16:30:04.787 [Thread-55] DEBUG simpleDateFormat Line:21  - Sat Oct 21 00:00:00 CST 2000

```

可以使用`JDK8`引入的`DateTimeFormatter`代替`SimpleDateFormat`时限日期转换

```java
public static void dateTimeFormatter() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    for (int i = 0; i < 100; i++) {
        new Thread(() -> {
            TemporalAccessor parse = dateTimeFormatter.parse("2000-10-21");
            log.debug("{}", parse);
        }).start();
    }
}
```

### 7.2 不可变设计

以`String`为例

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];

    /** Cache the hash code for the string */
    private int hash; // Default to 0
}
```



#### final的使用

修饰类则类不可被继承

修饰字段则字段引用不可修改



### 保护性拷贝

在构造新字符串对象时，会生成新的char[] value，对内容进行复制。这种通过创建副本对象来避免共享的手段称之为**保护性拷贝（defensive copy）**

```java
    /**
     * Allocates a new {@code String} so that it represents the sequence of
     * characters currently contained in the character array argument. The
     * contents of the character array are copied; subsequent modification of
     * the character array does not affect the newly created string.
     *
     * @param  value
     *         The initial value of the string
     */
    public String(char value[]) {
        this.value = Arrays.copyOf(value, value.length);
    }
```

### 7.3 无状态

没有成员变量就称之为**无状态**，没有任何成员变量的类是线程安全的。



## 8. 并发工具

### 8.1 线程池

#### 8.1.1 线程池的优点

- 风险不受控，线程上下文切换开销、系统资源有限
- 线程创建开销大（本地方法栈、虚拟机栈）
- 利用线程池管理并服用线程，控制最大并发数
- 实现任务线程队列缓存策略和拒绝机制
- 实现某些与实践相关的功能，如定时执行，周期执行等
- 隔离线程环境，比如，交易服务和搜索服务在同一台服务器上，分别开启两个线程池，交易线程的资源消耗明显要大。因此，通过配置独立的线程池，将较慢的交易服务与搜索服务个离开，避免个服务线程互相影响

#### 8.1.2 自定义线程池

![](.\picture\自定义线程池.jpg)



```java
```



#### 8.1.3 ThreadPoolExecutor

![ThreadPoolExecutor](\picture\image-20220415175439568.png)

##### 1. 线程池状态

```java
    /**
     * The main pool control state, ctl, is an atomic integer packing
     * two conceptual fields
     *   workerCount, indicating the effective number of threads
     *   runState,    indicating whether running, shutting down etc
     *
     * In order to pack them into one int, we limit workerCount to
     * (2^29)-1 (about 500 million) threads rather than (2^31)-1 (2
     * billion) otherwise representable. If this is ever an issue in
     * the future, the variable can be changed to be an AtomicLong,
     * and the shift/mask constants below adjusted. But until the need
     * arises, this code is a bit faster and simpler using an int.
     *
     * The workerCount is the number of workers that have been
     * permitted to start and not permitted to stop.  The value may be
     * transiently different from the actual number of live threads,
     * for example when a ThreadFactory fails to create a thread when
     * asked, and when exiting threads are still performing
     * bookkeeping before terminating. The user-visible pool size is
     * reported as the current size of the workers set.
     *
     * The runState provides the main lifecycle control, taking on values:
     *
     *   RUNNING:  Accept new tasks and process queued tasks
     *   SHUTDOWN: Don't accept new tasks, but process queued tasks
     *   STOP:     Don't accept new tasks, don't process queued tasks,
     *             and interrupt in-progress tasks
     *   TIDYING:  All tasks have terminated, workerCount is zero,
     *             the thread transitioning to state TIDYING
     *             will run the terminated() hook method
     *   TERMINATED: terminated() has completed
     *
     * The numerical order among these values matters, to allow
     * ordered comparisons. The runState monotonically increases over
     * time, but need not hit each state. The transitions are:
     *
     * RUNNING -> SHUTDOWN
     *    On invocation of shutdown(), perhaps implicitly in finalize()
     * (RUNNING or SHUTDOWN) -> STOP
     *    On invocation of shutdownNow()
     * SHUTDOWN -> TIDYING
     *    When both queue and pool are empty
     * STOP -> TIDYING
     *    When pool is empty
     * TIDYING -> TERMINATED
     *    When the terminated() hook method has completed
     *
     * Threads waiting in awaitTermination() will return when the
     * state reaches TERMINATED.
     *
     * Detecting the transition from SHUTDOWN to TIDYING is less
     * straightforward than you'd like because the queue may become
     * empty after non-empty and vice versa during SHUTDOWN state, but
     * we can only terminate if, after seeing that it is empty, we see
     * that workerCount is 0 (which sometimes entails a recheck -- see
     * below).
     */
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
```

ThreadPoolExecutor使用int的高3位表示线程池状态，低29位表示线程数量

| 状态       | 高三位 | 是否接收新任务 | 处理阻塞任务 | 说明                                        |
| ---------- | ------ | -------------- | ------------ | ------------------------------------------- |
| RUNNING    | 111    | 是             | 是           |                                             |
| SHUTDOWN   | 000    | 否             | 是           | 不会接收新任务，但会执行阻塞任务            |
| STOP       | 001    | 否             | 否           | 会中断正在执行的任务并抛弃阻塞队列任务      |
| TIDYING    | 010    | -              | -            | 任务全执行完毕，活动线程为0即将进入终结状态 |
| TERMINATED | 011    | -              | -            | 终结状态                                    |

这些信息存储在一个原子变量`ctl`中，目的是将线程池状态与线程个数合二为一，这样就可以用一次`cas`原子操作进行赋值

```java
//c 旧值，ctlOf返回值为新值
ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c)))
// rs 前3位表示线程池状态，wc 后29位代表线程格式，ctl是合并结果
private static int ctlOf(int rs, int wc) { return rs | wc; }
```



##### 2. 构造方法

```java
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
```

- `corePoolSize`核心线程数量（任务执行完时仍然会保留的线程数量）
- `maximumPoolSize`最大线程数量
- `keepAliveTime`生存时间，默认情况下，只有当线程池中的线程数大于`corePoolSize`时，`keepAliveTime`才会起作用，直到线程池中的线程数不大于`corePoolSize`，即当线程池中的线程数大于`corePoolSize`时，如果一个线程空闲的时间达到`keepAliveTime`，则会终止，直到线程池中的线程数不超过`corePoolSize`。但是如果调用了`allowCoreThreadTimeOut(boolean)`方法，在线程池中的线程数不大于`corePoolSize`时，`keepAliveTime`参数也会起作用，直到线程池中的线程数为0；
  - 救急线程
- `unit`时间单位，针对救急线程
- `workQueue`阻塞队列，当任务数量大于`maximumPoolSize`时，任务进入该阻塞队列
- `threadFactory`线程工厂，可以自定义线程名
- `handler`拒绝策略

##### 3. 执行流程

1. 线程池中刚开始没有线程，当一个任务提交后就会创建一个线程来执行任务
2. 当线程数量达到`corePoolSize`并且没有线程空闲，这时再加入任务，新加入的任务会被加入`workQueue`队列排队，直到有空闲的线程。
3. 如果队列选择了有界队列，那么任务超过队列大小时，会创建`maximumPoolSize - corePoolSize`数量的线程来救急
4. 如果线程到达了`maximumPoolSize`仍有新任务这时会执行拒绝策略，拒绝策略JDK提供了四种实现，其它著名框架也提供了实现
   - `AbortPolicy`，让调用者抛出`RejectedExecutionException`异常，这是默认策略
   - `CallerRunsPolicy`，让调用者执行任务
   - `DiscardOldestPolicy`，放弃最早加入队列的任务
   - `DiscardPolicy`，放弃任务
   - Dubbo的实现，在抛出`RejectedExecutionException`异常之前会记录日志，并dump线程栈信息。
   - `Netty`的实现，是创建一个新线程来执行
   - `ActiveMQ`，带超时等待（60s）尝试放入队列
   - `PinPoint`，使用了一个拒绝策略链，会逐一尝试策略链中每种拒绝策略
5. 当高峰过后，超过`corePoolSize`的救急线程如果一段时间没有任务做，需要节省资源，会终止，这个时间由`keepAliveTime`和`unit`决定

##### 4. newFixedThreadPool

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

##### 5. newCachedThreadPool

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```

##### 6. newSingleThreadExecutor

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```

##### 7.  提交任务

```java

// 提交任务task,用返回值Future获得任务执行结果
public <T> Future<T> submit(Callable<T> task)
// 提交集合中所有的任务，返回Future
public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
// 提交集合中所有任务，哪个任务先成功执行完毕，就返回此任务执行结果，其他任务取消
public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
```

##### 8. 关闭线程池

```java
    /**
     * 线程池状态变为SHUTDOWN
     * 不会接受新任务
     * 已提交任务会执行完
     * 此方法不会阻塞等待所有线程执行完
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted.
     * Invocation has no additional effect if already shut down.
     *
     * <p>This method does not wait for previously submitted tasks to
     * complete execution.  Use {@link #awaitTermination awaitTermination}
     * to do that.
     *
     * @throws SecurityException {@inheritDoc}
     */
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            checkShutdownAccess();
            advanceRunState(SHUTDOWN);
            interruptIdleWorkers();
            onShutdown(); // hook for ScheduledThreadPoolExecutor
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }
```



```java
    /**
     * 尝试打断所有正在执行的任务 停止正在等待的任务
     * 返回一个等待执行的任务列表
     * 不会接受新任务 
     *
     *
     * Attempts to stop all actively executing tasks, halts the
     * processing of waiting tasks, and returns a list of the tasks
     * that were awaiting execution. These tasks are drained (removed)
     * from the task queue upon return from this method.
     *
     * <p>This method does not wait for actively executing tasks to
     * terminate.  Use {@link #awaitTermination awaitTermination} to
     * do that.
     *
     * <p>There are no guarantees beyond best-effort attempts to stop
     * processing actively executing tasks.  This implementation
     * cancels tasks via {@link Thread#interrupt}, so any task that
     * fails to respond to interrupts may never terminate.
     *
     * @throws SecurityException {@inheritDoc}
     */
    public List<Runnable> shutdownNow() {
        List<Runnable> tasks;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            checkShutdownAccess();
            advanceRunState(STOP);
            interruptWorkers();
            tasks = drainQueue();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
        return tasks;
    }
```



##### 9. 任务调度线程池

在**任务调度线程池**加入之前，可以使用`java.util.Timer`来实现定时功能，Timer的优点在于简单易用，但由于所有任务都是由同一个线程来调度，因此所有任务都是串行执行的，同一时间只能有一个任务在执行，前一个任务出现延迟或异常都将影响到之后的任务

```java
    public static void test1() {
        Timer timer = new Timer();

        TimerTask timerTask1 = new TimerTask() {

            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("11111111");
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {

                log.debug("222222222");
            }
        };
        log.debug("start...");
        // 使用timer添加两个任务，希望它们在1s后执行
        timer.schedule(timerTask1, 1_000);
        // timer内部使用一个线程来顺序执行队列中的任务，因为timerTask1的延时影响到了timerTask2的执行
        // 若前面的异常发生了异常会影响后面任务的执行
        timer.schedule(timerTask2, 1_000);
    }
```

可以使用`Executors.newScheduledThreadPool`代替`Timer`解决上述问题

```java
    public static void test2() {
        //可以设置多个工作线程，使得多个任务同时执行
        //corePoolSize设为1时，多个任务串行执行
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1,
                r -> new Thread(null, r, "schedule thread"));

        scheduledExecutorService.schedule(() -> {
            log.debug("task 1");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //任务异常不会影响后续任务的执行
            log.debug("{}", 1 / 0);
        }, 1, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            log.debug("task 2");
        }, 1, TimeUnit.SECONDS);

//        scheduledExecutorService.scheduleAtFixedRate();
    }

    public static void test3() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        //延迟计算从任务开始时
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.debug("fix rate start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("fix rate end");

        }, 0, 2, TimeUnit.SECONDS);
    }

    public static void test4() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        //延迟计算从任务结束时
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            log.debug("fix rate start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("fix rate end");

        }, 0, 2, TimeUnit.SECONDS);
    }
```



##### 10. 正确处理线程池任务异常

```java
    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 提交Callable任务，如果任务内部出现异常，会在future.get()时抛出
        // 也可在任务内部捕获异常
        Future<Boolean> fu = executorService.submit(() -> {
            log.debug("{}", 1 / 0);
            return true;
        });
        log.debug("future.get()={}", fu.get());
    }
```

##### 11. 任务调度线程池之应用

```java
   /**
     * 每周四下午6点定时执行任务
     */
    public static void test1() {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

        long delay;
        long period;

        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        log.debug("now={}", now);

        // 获取周四的时间对象，获取的时间对象可能时在当前时间之前
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
//        log.debug("{}", time);

        //当前时间大于下次执行时间则加一星期
        if (now.compareTo(time) > 0) {
            time = time.plusWeeks(1);
        }
        log.debug("next time={}", time);

        //计算两个时间对象间隔的毫秒数
        delay = Duration.between(now, time).toMillis();
        // 一周毫秒数
        period = 7 * 24 * 60 * 60 * 1000;

        log.debug("delay={}", delay);


        schedule.scheduleAtFixedRate(() -> {
            log.debug("开始执行定时任务：{}", LocalDateTime.now());
        }, delay, period, TimeUnit.MILLISECONDS);
    }
```



##### 12. Tomcat线程池

![image-20220417204538862](\picture\image-20220417204538862.png)

- `LimitLatch`用来限流，可以控制最大连接数，类似`JUC`中的`Semaphore`
- `Acceptor`只负责接受新的`socket`
- `Poller`只负责监听`socket channel`是否有可读的IO事件
- 一旦可读，封装一个任务对象`sockeProcessor`，提交给`Executor`线程池处理
- `Executor`线程池中的工作线程最终负责处理请求

Tomcat线程池扩展了`ThreadPoolExecutor`，行为稍有不同

- 如果总数达到`maximumPoolSize`
  - 不会立即抛出`RejectedExecutionException`，会在第一次失败后再次尝试放入队列，如果还是失败则才抛出`RejectedExecutionException`

![image-20220417211604196](\picture\image-20220417211604196.png)

![image-20220417211655900](\picture\image-20220417211655900.png)



![image-20220417211950873](\picture\image-20220417211950873.png)

#### 8.1.4 Fork/Join

##### 概念

`Fork/Join`是`JDK７`加入的新的线程池实现，它体现的是一种分治思想，适用于能够进行任务拆分的`CPU`密集型运算

所谓任务拆分，是将一个大任务拆分为相同的小任务，直至不能拆分可以直接求解。跟递归相关的一些计算，如归并排序、斐波那契数列都可以使用分治思想进行求解

`Fork/Join` 在分治的基础上加入了多线程，可以把每个任务的分解和合并交给不同的线程来完成，进一步提升了运算效率

`Fork/Join`会默认创建与`CPU`核心数相同的线程池



##### 使用

```java
package com.hyh.jucutil.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author : huang.yaohua
 * @date : 2022/4/17 21:28
 */
@Slf4j(topic = "forkJoin")
public class ForkJoinTest {
    public static void main(String[] args) throws InterruptedException {
//        testMyTask(2000);
        testMyTask2(2000);
    }

    public static void testMyTask(int n) {
        // 1毫秒=100w纳秒
        long start = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        MyTask myTask = new MyTask(n);
        Integer compute = forkJoinPool.invoke(myTask);
        log.debug("{}", compute);
        log.debug("cost {}ms", (System.nanoTime() - start) / 1000_000);
    }

    public static void testMyTask2(int end) {
        // 1毫秒=100w纳秒
        long start = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        MyTask2 myTask = new MyTask2(1, end);
        Integer compute = forkJoinPool.invoke(myTask);
        log.debug("{}", compute);
        log.debug("cost {}ms", (System.nanoTime() - start) / 1000_000);
    }

}

/**
 * 计算1到n的和
 * 方式一
 * RecursiveAction 无返回值
 * RecursiveTask 有返回值
 */
@Slf4j(topic = "myTask")
class MyTask extends RecursiveTask<Integer> {

    private final int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            log.debug("if {}", n);
            return n;
        }
        // 求 1 到 n 的和 此任务拆分为
        // new MyTask(n) + new MyTask(n - 1) + new MyTask(n - 2)...new MyTask(1)
        MyTask task = new MyTask(n - 1);
        task.fork();
        log.debug("fork {},{}", n, task);

        int i = n + task.join();
        log.debug("join {},{}", n, task);
        return i;
    }
}

/**
 * 计算1到n的和
 * 方式二，优化使用二分法
 */
@Slf4j(topic = "myTask2")
class MyTask2 extends RecursiveTask<Integer> {

    private final int begin;

    private final int end;

    public MyTask2(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + ',' + end + '}';
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            log.debug("begin=end,{}", end);
            return end;
        }

        if (end - begin == 1) {
            log.debug("{}-{}=1,begin+end={}", end, begin, begin + end);
            return begin + end;
        }


        // 求 1 到 n 的和 此任务拆分为两部分
        int mid = (begin + end) / 2;
        MyTask2 task1 = new MyTask2(begin, mid);
        task1.fork();

        MyTask2 task2 = new MyTask2(mid + 1, end);
        task2.fork();

        log.debug("fork {} + {} = ?", task1, task2);

        int i = task1.join() + task2.join();
        log.debug("join {} + {} = {}", task1, task2, i);

        return i;
    }
}


```



### 8.2 JUC

#### 1. AQS原理

#### 2. RenentrantLock原理

#### 3. 读写锁

##### 3.1ReentrantReadWriteLock

当读操作远高于写操作时，这时使用**读写锁**，让**读-读**可以并发，提高性能

提供一个数据容器类，内部分别使用读锁保护数据的`read()`方法，写锁保护数据的`write()`方法

```java
package com.hyh.jucutil.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : huang.yaohua
 * @date : 2022/4/23 19:38
 */
@Slf4j(topic = "readWriteLockTest")
public class ReadWriteLockTest {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    /**
     * 读-读锁不互斥
     */
    public static void test1() {
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            log.debug("{}", dataContainer.read());
        }).start();

        new Thread(() -> {
            log.debug("{}", dataContainer.read());
        }).start();
    }

    /**
     * 读-写锁互斥
     */
    public static void test2() throws InterruptedException {
        DataContainer dataContainer = new DataContainer();

        new Thread(dataContainer::write).start();

        TimeUnit.MILLISECONDS.sleep(100);

        new Thread(() -> {
            log.debug("{}", dataContainer.read());
        }).start();
    }
}

@Slf4j(topic = "dataContainer")
class DataContainer {
    private Object data;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    public Object read() {
        log.debug("获取读锁");
        readLock.lock();
        try {
            log.debug("read");
            return data;
        } finally {
            log.debug("释放读锁");
            readLock.unlock();
        }
    }

    public void write() {
        log.debug("获取写锁");
        writeLock.lock();
        try {
            log.debug("write");
            this.data = "1";
        } finally {
            log.debug("释放写锁");
            writeLock.unlock();
        }
    }
}

```



##### 注意事项

- 读锁不支持条件变量
- 重入时升级不支持，即持有读锁的情况下获取写锁，会导致永久等待
- 冲入时降级支持，即持有写锁的情况下获取读锁

![image-20220423195922892](\picture\image-20220423195922892.png)

应用

```java
@Slf4j(topic = "readWriteDAOTest")
public class ReadWriteLockDAOTest {
    public static void main(String[] args) {
        BaseDAO baseDAO = new BaseDAO();
        test1(baseDAO);
        BaseDAO cacheDAO = new CachedBaseDAO();
        test1(cacheDAO);

    }

    /**
     * 无缓存
     * 每次都从数据库读
     * 数据库压力大
     * 高性能、高并发
     * 数据库并发有限
     * 数据库读取数据速度低于读取缓存
     */
    public static void test1(BaseDAO regionDAO) {
//        Map<String, Object> param = new HashMap<>();
        String region = "-977737102";
//        param.put("communityId", region);
        log.debug("========================query========================");
        String sql = "select 1 from wfm_region where region_no = ?";
        Map<String, Object> map = regionDAO.query(sql, region);

        System.out.println(map);
        map = regionDAO.query(sql, region);
        System.out.println(map);
        map = regionDAO.query(sql, region);
        System.out.println(map);
        log.debug("========================update========================");

        regionDAO.update("update wfm_region set a.region_name = ? where region_no = ?", "hyh", region);
        map = regionDAO.query(sql, region);
        System.out.println(map);
    }
}
@Slf4j(topic = "baseDao")
public class BaseDAO {
    private final JdbcTemplate jdbcTemplate = JDBCUtil.getJdbcTemplate();

    public Map<String, Object> query(String sql, Object... args) {
        log.debug("sql={},args={}", sql, args);
        return jdbcTemplate.queryForMap(sql, args);
    }

    public int update(String sql, Object... args) {
        log.debug("sql={},args={}", sql, args);
        return jdbcTemplate.update(sql, args);
    }
}
//带缓存的数据访问对象
public class CachedBaseDAO extends BaseDAO {

    //sql语句+参数作为key
    //HashMap线程不安全
    private Map<SqlPair, Map<String, Object>> cache = new HashMap<>();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    @Override
    public Map<String, Object> query(String sql, Object... args) {
        SqlPair sqlPair = new SqlPair(sql, args);
        Map<String, Object> map;
        readLock.lock();
        try {
            map = cache.get(sqlPair);
        } finally {
            readLock.unlock();
        }
        if (null != map) {
            return map;
        }


        writeLock.lock();
        try {
            //要使用双重检测
            // 若多个线程同时来查数据，
            // 首个线程读取成功后缓存会被更新，后面的线程可以直接从缓存查询数据
            map = cache.get(sqlPair);
            if (null != map) {
                return map;
            }
            map = super.query(sql, args);
            cache.put(sqlPair, map);
            return map;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int update(String sql, Object... args) {
        //先更新后清除缓存
        //先清除缓存后更新数据容易造成更大的数据不一致问题
        writeLock.lock();
        try {
            int update = super.update(sql, args);
            cache.clear();
            return update;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 缓存key
     */
    class SqlPair {
        private String sql;

        private Object[] args;

        public SqlPair(String sql, Object[] args) {
            this.sql = sql;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SqlPair sqlPair = (SqlPair) o;
            return sql.equals(sqlPair.sql) && Arrays.equals(args, sqlPair.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(sql);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }
}
```

![image-20220425215448648](\picture\image-20220425215448648.png)

![image-20220425214046216](\picture\image-20220425214046216.png)

![image-20220425214106161](\picture\image-20220425214106161.png)



##### ReentrantReadWriteLock原理

##### 3.2 StampedLock

自`JDK8`后加入，是为了进一步优化读性能，它的特点是使用读锁、写锁时都必须配合**戳**使用

![image-20220425224640201](\picture\image-20220425224640201.png)

##### StampedLock应用

```java
package com.hyh.jucutil.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

@Slf4j(topic = "stampedLock")
public class StampedLockTest {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test1() {
        StampedDataContainer dataContainer = new StampedDataContainer(0);
        new Thread(() -> {
            try {
                dataContainer.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                dataContainer.read(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void test2() throws InterruptedException {
        StampedDataContainer dataContainer = new StampedDataContainer(1);
        new Thread(() -> {
            try {
                dataContainer.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(500);
        new Thread(() -> {
            dataContainer.write(0);
        }).start();

    }
}

@Slf4j(topic = "container")
class StampedDataContainer {
    private int data;

    private final StampedLock lock = new StampedLock();

    public StampedDataContainer(int data) {
        this.data = data;
    }

    public int read(int readTime) throws InterruptedException {
        // 乐观锁 获取戳
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read lock {}", stamp);

        TimeUnit.SECONDS.sleep(1);

        //验证戳成功表明没有写操作，可以安全使用，否则要升级成读锁保证数据安全
        if (lock.validate(stamp)) {
            log.debug("optimistic lock finish {}", stamp);
            return data;
        }

        log.debug("upgrade to read lock");
        long readStamp = lock.readLock();
        log.debug("read lock {}", readStamp);
        try {
            TimeUnit.SECONDS.sleep(readTime);
            log.debug("read finish {}", readStamp);
            return data;
        } finally {
            log.debug("read unlock {}", readStamp);
            lock.unlockRead(readStamp);
        }
    }

    public void write(int data) {
        log.debug("write start");
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);

        try {
            this.data = data;
            log.debug("write finish {}", stamp);
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}

```

##### StampedLock缺点

- 不支持锁重入
- 不支持条件变量

#### 4. Semaphore

`Semaphore`（信号量）是用来控制同时访问特定资源的线程数量，通过协调各个线程以保证合理地使用公共资源。

`Semaphore`通过使用计数器来控制对共享资源的访问。 如果计数器大于0，则允许访问。 如果为0，则拒绝访问。 计数器所计数的是允许访问共享资源的许可。 因此，要访问资源，必须从信号量中授予线程许可。

原文链接：https://blog.csdn.net/warybee/article/details/111316932

##### Semaphore应用

```java
@Slf4j(topic = "semaphorePool")
class SemaphorePool {

    public static final int FREE = 0;

    public static final int BUSY = 1;
    /**
     * 连接池大小
     */
    private int poolSize;

    /**
     * 连接数组
     */
    private Connection[] connections;

    /**
     * 连接数组中每个连接的状态 1 忙 0 闲
     */
    private AtomicIntegerArray states;

    private Semaphore semaphore;

    public SemaphorePool(int poolSize) {
        this.poolSize = poolSize;
        semaphore = new Semaphore(poolSize);
        states = new AtomicIntegerArray(poolSize);
        connections = new MockConnection[poolSize];
        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnection("连接" + i);
        }
    }

    public Connection getConnection() {
        try {
            // 获取资源，计数减一
            // `Semaphore`通过使用计数器来控制对共享资源的访问。
            // 如果计数器大于0，则允许访问。 如果为0，则拒绝访问，线程在此等待
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        for (int i = 0; i < connections.length; i++) {
            //获取空闲连接
            if (states.compareAndSet(i, FREE, BUSY)) {
                Connection connection = connections[i];
                log.debug("获取连接{}", connection);
                return connection;
            }
        }
        return null;
    }

    public void free(Connection conn) {
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == conn) {
                log.debug("释放连接{}", conn);
                states.set(i, FREE);
                // 释放资源，计数加一
                semaphore.release();
                break;
            }
        }
    }
}
```

##### Semaphore原理

#### 5.CountDownLatch

CountDownLatch是一个同步工具类，用来协调多个线程之间的同步，或者说起到线程之间的通信（而不是用作互斥的作用）。

CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。使用一个计数器进行实现。计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。当计数器的值为0时，表示所有的线程都已经完成一些任务，然后在CountDownLatch上等待的线程就可以恢复执行接下来的任务

原文链接：https://blog.csdn.net/weixin_45976114/article/details/115305188

```java
@Slf4j(topic = "countDown")
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        log.debug("count {}", countDownLatch.getCount());
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();


        //计数变为0之前会一直阻塞在此
        countDownLatch.await();
        log.debug("countDown release");
    }
}
```

```java

    public static void test2() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(10,
                10, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        String[] s = new String[10];
        Random random = new Random();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int j = 0; j < s.length; j++) {
            int k = j;
            service.submit(() -> {
                for (int i = 0; i <= 100; i++) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s[k] = i + "%";
                    //这种方式打印，前面打印的结果会覆盖后面的
                    System.out.print("\r" + Arrays.toString(s));
                }
                countDownLatch.countDown();
            });
        }

        service.shutdown();
        countDownLatch.await();
        System.out.println("\ngame start");
    }
```



#### 6. CyclicBarrier

[CyclicBarrier](https://so.csdn.net/so/search?q=CyclicBarrier&spm=1001.2101.3001.7020) 是另外一种多线程并发控制工具。

和 [CountDownLatch](https://so.csdn.net/so/search?q=CountDownLatch&spm=1001.2101.3001.7020) 非常类似，它也可以实现线程间的计数等待，但它的功能比 CountDownLatch 更加复杂且强大。

CyclicBarrier 可以理解为循环栅栏。

```java
@Slf4j(topic = "cyclicBarrier")
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        testCyclicBarrier(3);
//        testCountDownLatch(3);
    }

    public static void testCyclicBarrier(int loopCount) {
        //CyclicBarrier 相比 countDownLatch ,可以重用，计数减为零后，计数重置为初始值
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            //计数减为零后会回调此方法
            log.debug("all task complete");
        });

        //要确保线程池大小和 cyclicBarrier计数大小相等
        //若不相等
        // 假如线程池大小大于CyclicBarrier计数
        //可能会出现这种情况： task1(执行1s)->task1(执行1s)->task2(执行2s)
        //没有按预想的task1->task2顺序执行
        ExecutorService ser = Executors.newFixedThreadPool(2);

        for (int i = 0; i < loopCount; i++) {
            ser.submit(() -> {
                log.debug("task1 start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cyclicBarrier.await();//计数减一 : 2 - 1
                    log.debug("task1 end");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            ser.submit(() -> {
                log.debug("task2 start");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cyclicBarrier.await();//计数减一 : 1 - 1
                    log.debug("task2 end");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

        }

        ser.shutdown();
    }

    public static void testCountDownLatch(int loopCount) throws InterruptedException {

        ExecutorService ser = Executors.newFixedThreadPool(2);
        for (int i = 0; i < loopCount; i++) {
            //CountDownLatch 不能重用，计数减为零后countDownLatch失效
            CountDownLatch countDownLatch = new CountDownLatch(2);

            ser.submit(() -> {
                log.debug("task1 start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                log.debug("task1 end");
            });

            ser.submit(() -> {
                log.debug("task2 start");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                log.debug("task2 end");
            });

            countDownLatch.await();
            log.debug("all task complete");
        }

        ser.shutdown();
    }
}
```

#### 7. 线程安全集合

![image-20220427224011020](\picture\image-20220427224011020.png)

- 遗留的线程安全集合：HashTable、Vector

- 使用Collections装饰的线程安全集合：Collections.synchronizedCollection、synchronizedSet、SynchronizedList、synchronizedMap等

- j.u.c下的各类线程安全集合，里面包含三类关键词：Blocking、CopyOnWrite、Concurrent

  - Blocking大部分实现基于锁，提供用来阻塞的方法
  - CopyOnWrite之类修改容器修改开销相对较重
  - Concurrent类型的容器
    - 内部很多操作使用CAS优化，一般可以提供较高的吞吐量
    - 弱一致性
      - 遍历时弱一致性，例如：当利用迭代器遍历时，如果容器发生修改，迭代器仍然可以进行遍历，这时的内容是旧的
      - 读取弱一致性
      - 获取大小弱一致性，size操作未必是100%准确

  > 非线程安全类容器，遍历时如果发生了修改会使用fast-fail机制，让遍历立刻失败，抛出ConcurrentModificationException，不再继续遍历

  

提交

## Lock接口



## 线程间通信



## 线程间定制化通信



## 集合的线程安全



## 多线程锁



## Callable接口



## JUC强大的辅助类



## ReentrantReadWriteLock读写锁



## BlockingQueue阻塞队列



## ThreadPool线程池



## Fork/Join分支合并框架



## CompeletalbeFuture异步回调

