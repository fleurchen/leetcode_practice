package ratingLimiting.test;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowRateLimiterTest {
    private final int limit;
    private final long windowtime;
    private Queue<Long> queueStamp;

    public SlidingWindowRateLimiterTest(int limit,long windowtime){
        this.limit = limit;
        this.windowtime = windowtime;
        this.queueStamp = new LinkedList<>();;
    }

    public synchronized boolean tryAcquire(){
        long now = System.currentTimeMillis();
        if(!queueStamp.isEmpty() && now - queueStamp.peek() > windowtime){
            queueStamp.poll();
        }
        if(queueStamp.size() < limit){
            queueStamp.offer(now);
            return true;
        }
        return false;
    }
}
