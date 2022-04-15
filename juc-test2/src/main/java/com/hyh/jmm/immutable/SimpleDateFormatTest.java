package com.hyh.jmm.immutable;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author : huang.yaohua
 * @date : 2022/4/14 16:26
 */
@Slf4j(topic = "simpleDateFormat")
public class SimpleDateFormatTest {
    public static void main(String[] args) {
        dateTimeFormatter();
    }

    public static void simpleDateFormat() {
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

    public static void dateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                TemporalAccessor parse = dateTimeFormatter.parse("2000-10-21");
                log.debug("{}", parse);
            }).start();
        }
    }
}
