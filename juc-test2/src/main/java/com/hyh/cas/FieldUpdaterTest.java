package com.hyh.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

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
