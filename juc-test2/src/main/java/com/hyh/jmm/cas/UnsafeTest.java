package com.hyh.jmm.cas;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

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
