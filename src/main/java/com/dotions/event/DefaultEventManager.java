package com.dotions.event;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dotions.event.utils.ListenerWrapper;
import com.dotions.event.utils.Utils;

/**
 * <p>
 * 默认事件管理器（以 order 顺序从小到大依次执行 listener。）
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class DefaultEventManager extends AbstractEventManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEventManager.class);

    @Override
    public void executeInternal(EventContext context) {
        List<ListenerWrapper> list = context.getListeners();
        Event event = context.getEvent();

        // 按顺序处理事件
        list.parallelStream().filter(Objects::nonNull).sorted((a, b) -> a.getOrder() - b.getOrder())
                .map(ListenerWrapper::getListener).forEach(listener -> {
                    try {
                        // 处理事件
                        listener.onEvent(event);
                        if (logger.isInfoEnabled()) {
                            logger.info("[executeInternal] done. listener=" + Utils.getClassName(listener) + ", event=" + event);
                        }
                    } catch (Exception e) {
                        logger.error("[executeInternal] fail. event=" + event + ", listener=" + Utils.getClassName(listener), e);
                    }
                });
    }

}
