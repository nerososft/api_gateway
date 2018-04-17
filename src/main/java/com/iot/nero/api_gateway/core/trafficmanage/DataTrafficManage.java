package com.iot.nero.api_gateway.core.trafficmanage;
/**
 * Created by wujindong on 2017/9/4.
 */
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Preconditions;

public class DataTrafficManage {

    // 默认桶大小个数 即最大瞬间流量是64M
    private static final int DEFAULT_BUCKET_SIZE = 1024 * 1024*64;

    // 一个桶的单位是1字节
    private int everyTokenSize = 1;

    // 瞬间最大流量
    private int maxFlowRate;

    // 平均流量
    private int avgFlowRate;

    // 队列来缓存桶数量：最大的流量峰值就是 = everyTokenSize*DEFAULT_BUCKET_SIZE 64M = 1 * 1024 * 1024 * 64
    //BolckingQueue一种特殊的队列 满了不能存 空了不能取
    private ArrayBlockingQueue<Byte> tokenQueue = new ArrayBlockingQueue<Byte>(DEFAULT_BUCKET_SIZE);

    //ScheduledExecutorService定时周期执行指定的任务
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    //判断限流是否正在执行
    private volatile boolean isStart = false;

    //线程锁与syncronized相同但是更为先进
    private ReentrantLock lock = new ReentrantLock(true);

    //默认一个字符是一个字节
    private static final byte A_CHAR = 'a';

    public DataTrafficManage() {
    }

    public ArrayBlockingQueue<Byte> getTokenQueue() {
        return tokenQueue;
    }

    public void setTokenQueue(ArrayBlockingQueue<Byte> tokenQueue) {
        this.tokenQueue = tokenQueue;
    }

    public DataTrafficManage(int maxFlowRate, int avgFlowRate) {
        this.maxFlowRate = maxFlowRate;
        this.avgFlowRate = avgFlowRate;
    }

    public DataTrafficManage(int everyTokenSize, int maxFlowRate, int avgFlowRate) {
        this.everyTokenSize = everyTokenSize;
        this.maxFlowRate = maxFlowRate;
        this.avgFlowRate = avgFlowRate;
    }

    public void addTokens(Integer tokenNum) {
// 若是桶已经满了，就不再家如新的令牌
        for (int i = 0; i < tokenNum; i++) {
            //向队列中加入每个请求
            tokenQueue.offer(Byte.valueOf(A_CHAR));
        }
        //System.out.println("size is "+tokenQueue.size());
    }

    public DataTrafficManage build() {

        start();
        return this;
    }

    /**
     * 获取足够的令牌个数
     *
     * @return
     */
    public boolean getTokens(byte[] dataSize) {
        //java的Preconditions是JVM自带的检查运行时内容的函数
        Preconditions.checkNotNull(dataSize);
        Preconditions.checkArgument(isStart, "please invoke start method first !");

        int needTokenNum = dataSize.length / everyTokenSize + 1;// 传输内容大小对应的桶个数

        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            System.out.println("QueueSize is "+tokenQueue.size());
            boolean result = needTokenNum <= tokenQueue.size(); // 是否存在足够的桶数量
            if (!result) {
                return false;
            }

            int tokenCount = 0;
            for (int i = 0; i < needTokenNum; i++) {
                //取走队列的首位
                Byte poll = tokenQueue.poll();
                if (poll != null) {
                    tokenCount++;
                }
            }
            System.out.println("tokenCount is "+tokenCount);
            System.out.println("needTokenNum is "+needTokenNum);
            return tokenCount == needTokenNum;
        } finally {
            lock.unlock();
        }
    }

    public void start() {

// 初始化桶队列大小
        if (maxFlowRate != 0) {
            tokenQueue = new ArrayBlockingQueue<Byte>(maxFlowRate);
            System.out.println("tok size is "+tokenQueue.size());
        }

// 初始化令牌生产者
        /*
            public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
            long initialDelay,
            long period,
            TimeUnit unit);
            command：执行线程
            initialDelay：初始化延时
            period：两次开始执行最小间隔时间
            unit：计时单位
         */
        TokenProducer tokenProducer = new TokenProducer(avgFlowRate, this);
        scheduledExecutorService.scheduleAtFixedRate(tokenProducer, 0, 1, TimeUnit.SECONDS);
        isStart = true;

    }

    public void stop() {
        isStart = false;
        scheduledExecutorService.shutdown();
    }

    public boolean isStarted() {
        return isStart;
    }

    class TokenProducer implements Runnable {

        private int avgFlowRate;
        private DataTrafficManage tokenBucket;

        public TokenProducer(int avgFlowRate, DataTrafficManage tokenBucket) {
            this.avgFlowRate = avgFlowRate;
            this.tokenBucket = tokenBucket;
        }

        public void run() {
            tokenBucket.addTokens(avgFlowRate);
        }
    }

    public static DataTrafficManage newBuilder() {
        return new DataTrafficManage();
    }

    public DataTrafficManage everyTokenSize(int everyTokenSize) {
        this.everyTokenSize = everyTokenSize;
        return this;
    }

    public DataTrafficManage maxFlowRate(int maxFlowRate) {
        this.maxFlowRate = maxFlowRate;
        return this;
    }

    public DataTrafficManage avgFlowRate(int avgFlowRate) {
        this.avgFlowRate = avgFlowRate;
        return this;
    }
}

