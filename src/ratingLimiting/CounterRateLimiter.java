package ratingLimiting;

/**
 * 计数器
 * 记录上一次处理时间
 * 当当前处理时间与上一次处理时间超出时间限制时，则将计数器清零
 * 比较计数器与当前的限制，
 * 当未超过限制，则计数器加一
 */
public class CounterRateLimiter {
    private final int limit;
    private final long windowTime;
    private int count;
    private long lastTime;
    public CounterRateLimiter(int limit,long windowTime){
        this.limit = limit;
        this.windowTime = windowTime;
        count=0;
        lastTime=System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire(){
        long now = System.currentTimeMillis();
        if(now-lastTime>windowTime){
            lastTime=now;
            count = 0;
        }
        if(count < limit){
            count++;
            return true;
        }
        return false;
    }
}
