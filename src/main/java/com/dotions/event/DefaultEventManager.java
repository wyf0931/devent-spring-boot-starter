package com.dotions.event;

import java.util.List;

import com.dotions.event.utils.ListenerWrapper;

/**
 * <p>默认事件管理器（以 order 顺序从小到大依次执行 listener。）</p>
 * Created by Dotions on 2017-10-22.
 */
public class DefaultEventManager extends AbstractEventManager {

	/**
	 * @param properties
	 */
	public DefaultEventManager() {
		super();
	}

	@Override
	public void executeInternal(EventContext context) {
		List<ListenerWrapper> list = context.getListeners();
		Event event = context.getEvent();

		// 按顺序处理事件
		list.parallelStream().sorted((a, b) -> a.getOrder() - b.getOrder()).map(ListenerWrapper::getListener)
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
