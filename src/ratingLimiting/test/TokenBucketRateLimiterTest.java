package ratingLimiting.test;

public class TokenBucketRateLimiterTest {
    private final int rate;
    private final int capacity;
    private  int tokens;
    private long lapsedTime;

    public TokenBucketRateLimiterTest(int rate,int capacity){
        this.rate = rate;
        this.capacity = capacity;
        this.tokens = capacity;
        this.lapsedTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire(){
        long now = System.currentTimeMillis();
        int loseToken = (int)((now-lapsedTime)*rate)/1000;
        tokens = Math.min(capacity,tokens+loseToken);
        if(tokens>0){
            tokens--;
            return true;
        }
        return false;
    }


}
