package com.dotions.event.utils;

import com.dotions.event.Listener;

/**
 * <p>
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class ListenerProxy {

	private Listener listener;
	private int order;
	private int eventType;

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

	/**
	 * @param listener
	 * @param order
	 * @param eventType
	 */
	public ListenerProxy(Listener listener, int order, int eventType) {
		super();
		this.listener = listener;
		this.order = order;
		this.eventType = eventType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
