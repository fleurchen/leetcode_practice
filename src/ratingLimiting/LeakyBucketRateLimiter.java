package ratingLimiting;

/**
 * 漏桶原理
 * 获取时间的流逝
 * 获取漏水的多少
 * 桶中的水去掉漏出的水
 * 比较桶中剩余的水与容量的关系
 */
public class LeakyBucketRateLimiter {
    private final int capacity;
    private final int rate;
    private long lastStampTime;
    private int water;

    public LeakyBucketRateLimiter(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.lastStampTime = System.currentTimeMillis();
        this.water = 0;
    }

    public synchronized boolean tryAcquire(){
        long now = System.currentTimeMillis();
        long lapsedTime = now -lastStampTime;
        int lapsedWater = (int)lapsedTime*rate/1000;
        water = Math.max(0,water-lapsedWater);
        lastStampTime = now;

        if(water<capacity){
            water++;
            return true;
        }
        return false;
    }
}
