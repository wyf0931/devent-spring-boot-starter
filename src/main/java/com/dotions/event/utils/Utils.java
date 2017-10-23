package com.dotions.event.utils;

/**
 * <p>
 * 常用工具方法
 * </p>
 * 
 * @author wangyunfei 2017-10-23
 */
public abstract class Utils {
    
    public static final String EMPTY_STRING = "";

    /**
     * 获取指定对象所属类的名称
     * @param obj 指定的对象
     * */
    public static String getClassName(Object obj) {
        if (null == obj) {
            return EMPTY_STRING;
        }
        return obj.getClass().getName();
    }

    /**
     * 获取指定对象所属类的名称
     * @param obj 指定的对象
     * */
    public static String getClassSimpleName(Object obj) {
        if (null == obj) {
            return EMPTY_STRING;
        }
        return obj.getClass().getSimpleName();
    }

    /**
     * 计算耗时
     * @param startTimestamp 开始时间戳
     * */
    public static long cost(long startTimestamp) {
        return System.currentTimeMillis() - startTimestamp;
    }
}
