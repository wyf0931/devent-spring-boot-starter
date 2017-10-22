package com.dotions.event;

import java.util.List;

import com.dotions.event.configuration.EventProperties;
import com.dotions.event.utils.ListenerProxy;

/**
 * Created by Dotions on 2017-10-22.
 */
public class DefaultEventManager extends AbstractEventManager {

	/**
	 * @param properties
	 */
	public DefaultEventManager(EventProperties properties) {
		super(properties);
	}

	public void executeInternal(EventContext context) {
		List<ListenerProxy> list = context.getListeners();
		Event event = context.getEvent();

		// 按顺序处理事件
		list.parallelStream().sorted((a, b) -> a.getOrder() - b.getOrder()).map(ListenerProxy::getListener)
				.forEach(listener -> {
					try {
						// 处理事件
						listener.onEvent(event);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				});
	}

}
