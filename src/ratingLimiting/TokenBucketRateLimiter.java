package ratingLimiting;

/**
 * 令牌桶算法
 * 设置桶容量和令牌生成的速度
 * 获取距离上次填充时间段生成的令牌桶的数量
 * 如果令牌数量大于零，则可以获取到
 */
public class TokenBucketRateLimiter {
    private final int capacity;
    private final int rate;
    private int tokens;
    private long lastReFillTime;

    public TokenBucketRateLimiter(int capacity,int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.tokens = capacity;
        this.lastReFillTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        long laspsedTime = now - lastReFillTime;
        int generateTokens = (int)(laspsedTime*rate)/1000;
        tokens = Math.min(capacity,tokens+generateTokens);
        lastReFillTime = now;
        if(tokens>0){
            tokens--;
            return true;
        }
        return false;
    }
}
