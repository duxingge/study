package com.wjx.java.collection;

import com.wjx.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * 使用 延迟队列{@link DelayQueue} 实现缓存与数据库不一致问题的延迟双删解决方案
 * 缺点：可能随着JVM的死亡导致，丢失删除的动作
 * 缺点改进改进：可以考虑使用将延迟删除的数据放到redis中，通过lua+Zset结构来实现；也可以考虑使用MQ来实现
 *
 * @Author wangjiaxing
 * @Date 2023/6/13
 */
public class TDelayQueue {

    static DelayQueue<DelKey> delayQueue = new DelayQueue();
    static Map<String,String> cache = new HashMap<>();

    public static void main(String[] args) {
        delayQueue.put(new DelKey("k1"));

        cache.put("k1","v1");
        cache.put("k2","v2");
        cache.put("k3","v3");

        CompletableFuture.runAsync(() -> {
            while (true) {
                DelKey delKey = delayQueue.poll();
                if(delKey!=null) {
                    String key = delKey.getKey();
                    cache.remove(key);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        CompletableFuture<Void> printFuture = CompletableFuture.runAsync(() -> {
            while (true) {
                System.out.println(" cache: " + JsonUtil.toString(cache));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        printFuture.join();

    }




    static class DelKey implements Delayed {
        private String key;
        private long initTimestamp;
        private int delaySeconds = 5 ;

        public DelKey(String key) {
            this.key = key;
            initTimestamp = System.currentTimeMillis();
        }

        public DelKey(String key, int delaySeconds) {
            this.key = key;
            this.delaySeconds = delaySeconds;
            initTimestamp = System.currentTimeMillis();
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(SECONDS.toMillis(delaySeconds) - (System.currentTimeMillis()-initTimestamp) , MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (getDelay(TimeUnit.SECONDS) - o.getDelay(TimeUnit.SECONDS));
        }
    }
}
