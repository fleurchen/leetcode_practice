package ratingLimiting.test;

public class LeakyBucketRateLimiterTest {
    private final int capacity;
    private final int rate;
    private int water;
    private long lastTimestamp;

    public LeakyBucketRateLimiterTest(int rate,int capacity){
        this.rate = rate;
        this.capacity = capacity;
        this.water = 0;
        this.lastTimestamp = System.currentTimeMillis();

    }

    public synchronized boolean tryAcquire(){
        long now = System.currentTimeMillis();
        long lapseTimestamp = now - lastTimestamp;
        int waterContent = (int)(lapseTimestamp * rate )/1000;
        water = Math.max(0,water-waterContent);
        lastTimestamp = now;
        if(water < capacity){
            water++;
            return true;
        }
        return false;
    }
}
