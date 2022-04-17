package com.hyh.jucutil.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : huang.yaohua
 * @date : 2022/4/17 20:23
 */
@Slf4j(topic = "schedule")
public class ScheduleTest {
    public static void main(String[] args) {
        test1();
    }

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
}
