package com.dotions.event;

import java.util.List;

import com.dotions.event.utils.ListenerWrapper;

/**
 * Created by Dotions on 2017-10-22.
 */
public class EventContext {

    /**
     * 事件体
     */
    private final Event event;

    /**
     * 待执行的监听器
     */
    private final List<ListenerWrapper> listeners;

    /**
     * @param event
     * @param listeners
     */
    public EventContext(Event event, List<ListenerWrapper> listeners) {
        super();
        this.event = event;
        this.listeners = listeners;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return the listeners
     */
    public List<ListenerWrapper> getListeners() {
        return listeners;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EventContext [event=");
        builder.append(event);
        builder.append(", listeners=");
        builder.append(listeners);
        builder.append("]");
        return builder.toString();
    }

}
