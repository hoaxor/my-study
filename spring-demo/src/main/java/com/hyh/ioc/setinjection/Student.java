package com.hyh.ioc.setinjection;

import java.util.Date;

/**
 * @author : huang.yaohua
 * @date : 2022/5/14 23:58
 */
public class Student {
    private int age;

    private String student;

    private Date date;

    private School school;

    public Student(int age, String student, Date date) {
        this.age = age;
        this.student = student;
        this.date = date;
    }

    public Student() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public School getSchool() {
        return school;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", student='" + student + '\'' +
                ", date=" + date +
                ", school='" + school + '\'' +
                '}';
    }
}
