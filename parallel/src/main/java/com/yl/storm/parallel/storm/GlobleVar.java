package com.yl.storm.parallel.storm;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2016/8/27.
 */
public class GlobleVar {
    private static AtomicLong s_totalNum = new AtomicLong();

    public static void increase(){
        long num = s_totalNum.get();
        if(num < Long.MAX_VALUE){
            s_totalNum.getAndIncrement();
        } else {
            s_totalNum.getAndSet(0);
        }
    }

    public static long getNum(){
        return s_totalNum.get();
    }

}
