package com.hyh.jmm.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "twoPhase")
public class TwoPhaseTerminationTest {


    public static void main(String[] args) {

    }


}

@Slf4j(topic = "test1")
class TwoPhaseTermination {
    private Thread thread;

    public void start() {
        thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.debug("interrupted");
                    return;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //若在休眠时被打断， 抛出异常后打断标记会被重置 需要回复打断标记
                    Thread.currentThread().interrupt();
                }
            }
        }, "t1");
        thread.start();


    }

    public void stop() {
        thread.interrupt();
    }
}

@Slf4j(topic = "test2")
class TwoPhaseTerminationVolatile {
    private Thread thread;

    private volatile boolean stopFlag;

    public void start() {
        thread = new Thread(() -> {
            while (true) {
                if (stopFlag) {
                    log.debug("stopped");
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");
        thread.start();


    }

    public void stop() {
        stopFlag = true;
    }
}
