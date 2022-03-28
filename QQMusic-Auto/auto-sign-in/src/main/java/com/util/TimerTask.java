package com.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Consumer;

public class TimerTask {

    private final static Log LOG = LogFactory.getLog(TimerTask.class);

    private final static Vector<Task> TASKS = new Vector<>();

    public static void add(Task task){
        TASKS.add(task);
    }

    public static Vector<Task> getTasks(){
        return TASKS;
    }

    static {
        ThreadPoolExecutorUtils.execute(()->{
            while (true){
                try {
                    LOG.debug(String.format("目前任务数：%d", TASKS.size()));
                    Thread.sleep(10000);
                    for (int i = 0; i< TASKS.size(); i++){
                        Task task = TASKS.get(i);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        LOG.debug(String.format("任务%d： 预定执行时间：%s 剩余执行时间：%d分钟",i,dateFormat.format(task.startTime),(task.startTime.getTime()-System.currentTimeMillis())/1000/60));
                        if(task.startTime.before(Calendar.getInstance().getTime())){
                            LOG.debug(String.format("准备执行任务：%d",i));
                            try {
                                task.run();
                            }catch (Throwable e){
                                LOG.error("run task error");
                                LOG.error(e);
                                e.printStackTrace();
                            }
                            LOG.debug(String.format("任务%d正在执行中",i));
                            TASKS.remove(i);
                            LOG.debug(String.format("任务%d已移除",i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("run tasks error");
                    LOG.error(e);
                }
            }
        });
    }


    public static class Task{

        private final String uuid;
        private final Date startTime;
        private final Consumer<Task> consumer;
        private final String remark;

        public String getUuid() {
            return uuid;
        }

        public Task(Date startTime, Consumer consumer, String remark){
            this.startTime = startTime;
            this.consumer = consumer;
            this.remark = remark;

            this.uuid = UUID.randomUUID().toString();
        }

        public void run(){
            consumer.accept(this);
        }

        public Date getStartTime() {
            return startTime;
        }

        public String getRemark(){
            return remark;
        }

    }

}
