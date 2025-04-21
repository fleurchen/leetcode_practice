package ratingLimiting.test;

public class CounterRateLimiterTeat {
    private int count;
    private final long windowtime;
    private final int limit;
    private long lasttime;

    public CounterRateLimiterTeat(int limit,long windowtime){
        this.limit = limit;
        this.windowtime = windowtime;
        this.count = 0;
        this.lasttime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire(){
        long now = System.currentTimeMillis();
        if(now-lasttime > windowtime){
            lasttime = now;
            count=0;
        }
        if(count<limit){
            count++;
            return true;
        }
        return false;
    }
}
