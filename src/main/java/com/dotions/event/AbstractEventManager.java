package com.dotions.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.util.Assert;

import com.dotions.event.configuration.EventProperties;
import com.dotions.event.utils.ListenerProxy;
import com.dotions.event.utils.NamedThreadFactory;

/**
 * Created by Dotions on 2017-10-22.
 */
public abstract class AbstractEventManager {

	private static final String EVENT_TYPE_PREFIX = "E_T_";

	/**
	 * Event type ———> Event handler list.
	 */
	protected Map<String, List<ListenerProxy>> eventHandlerMap = new HashMap<String, List<ListenerProxy>>();

	protected LinkedBlockingQueue<Runnable> eventQueue = new LinkedBlockingQueue<Runnable>();

	protected NamedThreadFactory factory;
	private ThreadPoolExecutor executor;

	protected EventProperties properties;

	public AbstractEventManager(EventProperties properties) {
		Assert.notNull(properties, "Event properties must not be null.");
		String threadPrefix = properties.getThreadNamePrefix();
		factory = new NamedThreadFactory(threadPrefix);

		// Initialize event handler executor pool.
		int size = properties.getThreadPoolSize();
		executor = new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, eventQueue, factory);
	}

	protected void execute(Event event) {
		List<ListenerProxy> listeners = getListeners(event.getType());
		if (listeners.isEmpty()) {
			// TODO: logger
			System.out.println("[warn] Not found listener, event=" + event);
			return;
		}

		// Create event context.
		EventContext context = new EventContext(event, listeners);

		// Execute custom method
		executor.execute(() -> {
			long ts = System.currentTimeMillis();
			executeInternal(context);
			System.out.println("[execute] context=" + context + ", cost=" + (System.currentTimeMillis() - ts));
		});
	}

	/**
	 * custom method
	 */
	protected abstract void executeInternal(EventContext context);

	public synchronized void addListener(int eventType, int order, Listener listener) {
		ListenerProxy lp = new ListenerProxy(listener, order, eventType);

		System.err.println("[add] lp=" + lp);

		List<ListenerProxy> list = getListeners(eventType);
		if (list.isEmpty()) {
			list = new ArrayList<>();
		}

		list.add(lp);
		eventHandlerMap.put(buildEventTypeName(eventType), list);
	}

	public List<ListenerProxy> getListeners(int eventType) {
		String eType = buildEventTypeName(eventType);
		List<ListenerProxy> list = eventHandlerMap.get(eType);
		return list == null ? Collections.emptyList() : list;
	}

	private static final String buildEventTypeName(int type) {
		return EVENT_TYPE_PREFIX + type;
	}

}
