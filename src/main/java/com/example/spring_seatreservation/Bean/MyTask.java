package com.example.spring_seatreservation.Bean;

/**
 * @Author: crush
 * @Date: 2021-07-29 15:35
 * version 1.0
 */
public class MyTask {
    /**
     * 动态任务名
     */
    private String name;

    /**
     * 设定动态任务开始时间
     */
    private long start;

    Runnable runnable;

    public MyTask(String name, long start, Runnable runnable) {
        this.name = name;
        this.start = start;
        this.runnable = runnable;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public MyTask setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public String getName() {
        return name;
    }

    public MyTask setName(String name) {
        this.name = name;
        return this;
    }

    public long getStart() {
        return start;
    }

    public MyTask setStart(long start) {
        this.start = start;
        return this;
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "name='" + name + '\'' +
                ", start=" + start +
                '}';
    }
}
