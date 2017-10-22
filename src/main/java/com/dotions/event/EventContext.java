package com.dotions.event;

import java.util.List;

import com.dotions.event.utils.ListenerProxy;

/**
 * Created by Dotions on 2017-10-22.
 */
public class EventContext {

	private final Event event;

	private final List<ListenerProxy> listeners;

	/**
	 * @param event
	 * @param listeners
	 */
	public EventContext(Event event, List<ListenerProxy> listeners) {
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
	public List<ListenerProxy> getListeners() {
		return listeners;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
