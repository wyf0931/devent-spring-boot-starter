package com.dotions.event.utils;

import com.dotions.event.Listener;

/**
 * <p>
 * {@code Listener} 的包装类
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class ListenerWrapper {

    /**
     * Listener 对象
     */
    private Listener listener;

    /**
     * Listener 执行顺序
     */
    private int order;

    /**
     * Listener 监听的事件类型
     */
    private int eventType;

    /**
     * @param listener
     * @param order
     * @param eventType
     */
    public ListenerWrapper(Listener listener, int order, int eventType) {
        super();
        this.listener = listener;
        this.order = order;
        this.eventType = eventType;
    }

    /**
     * @return the listener
     */
    public Listener getListener() {
        return listener;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @return the eventType
     */
    public int getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListenerProxy [listener=");
        builder.append(listener);
        builder.append(", order=");
        builder.append(order);
        builder.append(", eventType=");
        builder.append(eventType);
        builder.append("]");
        return builder.toString();
    }

}
