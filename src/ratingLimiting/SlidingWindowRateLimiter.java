package ratingLimiting;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 滑动窗口限流方法
 * 设置窗口队列
 * 先将队列中过期的队列清除
 * 比较队列的长度与限制次数的关系
 * 未超过限制则入队处理
 */
public class SlidingWindowRateLimiter {
    private final int limit;
    private final long windowTime;
    private Queue<Long>  requireTimestamp;

    public SlidingWindowRateLimiter(int limit, long windowTime){
        this.limit = limit;
        this.windowTime = windowTime;
        this.requireTimestamp = new LinkedList<Long>();
    }

    public synchronized boolean tryAcquire(){
        long now = System.currentTimeMillis();
        if(!requireTimestamp.isEmpty() &&now - requireTimestamp.peek() > windowTime){
            requireTimestamp.poll();
        }
        if(requireTimestamp.size()<limit){
            requireTimestamp.offer(now);
            return true;
        }
        return false;
    }
}
