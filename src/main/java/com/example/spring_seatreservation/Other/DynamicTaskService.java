package com.example.spring_seatreservation.Other;

import com.example.spring_seatreservation.Bean.MyTask;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

/**
 * @author crush
 */
@Component
public class DynamicTaskService {

    /**
     * 以下两个都是线程安全的集合类。
     */
    public Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();
    public List<String> taskList = new CopyOnWriteArrayList<String>();


    private final ThreadPoolTaskScheduler syncScheduler;

    public DynamicTaskService(ThreadPoolTaskScheduler syncScheduler) {
        this.syncScheduler = syncScheduler;
    }

    /**
     * 查看已开启但还未执行的动态任务
     *
     * @return
     */
    public List<String> getTaskList() {
        return taskList;
    }


    /**
     * 添加一个动态任务
     * 任务名：目标状态码-座位id
     *
     * @param myTask
     * @return
     */
    public boolean add(MyTask myTask) {
        // 此处的逻辑是 ，如果当前已经有这个名字的任务存在，先删除之前的，再添加现在的。（即重复就覆盖）
        if (null != taskMap.get(myTask.getName())) {
            stop(myTask.getName());
        }

        Date startTime = new Date(myTask.getStart());

        // schedule :调度给定的Runnable ，在指定的执行时间调用它。
        //一旦调度程序关闭或返回的ScheduledFuture被取消，执行将结束。
        //参数：
        //任务 – 触发器触发时执行的 Runnable
        //startTime – 任务所需的执行时间（如果这是过去，则任务将立即执行，即尽快执行）
        ScheduledFuture<?> schedule = syncScheduler.schedule(myTask.getRunnable(), startTime);
        taskMap.put(myTask.getName(), schedule);
        taskList.add(myTask.getName());
        return true;
    }

    /**
     * 停止任务
     *
     * @param name
     * @return
     */
    public boolean stop(String name) {
        if (null == taskMap.get(name)) {
            return false;
        }
        ScheduledFuture<?> scheduledFuture = taskMap.get(name);
        scheduledFuture.cancel(true);
        taskMap.remove(name);
        taskList.remove(name);
        return true;
    }
}
