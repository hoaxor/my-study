package com.hyh.jmm.pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "balking")
public class BalkingTest {
    //标识是否已经执行过start
    private boolean flag;

    public void start() {
        synchronized (this) {
            if (flag) {
                return;
            }
            flag = true;
        }

        log.debug("do something");
    }
}
