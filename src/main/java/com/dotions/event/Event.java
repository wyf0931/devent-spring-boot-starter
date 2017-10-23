package com.dotions.event;

import java.io.Serializable;

/**
 * <p>
 * Event 对象
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Object source;

    protected int type;

    /**
     * @param source
     * @param type
     */
    public Event(Object source, int type) {
        super();
        this.source = source;
        this.type = type;
    }

    /**
     * 
     */
    public Event() {
        super();
    }

    /**
     * @return the source
     */
    public Object getSource() {
        return source;
    }

    /**
     * @return the source
     */
    public <T> T getSource(Class<T> clazz) {
        return clazz.cast(source);
    }

    /**
     * @param source
     *        the source to set
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     *        the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Event [source=");
        builder.append(source);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }

}
