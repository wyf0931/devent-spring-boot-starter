package com.dotions.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * <p>
 * 默认事件服务实现类
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class DefaultEventServiceImpl implements IEventService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEventServiceImpl.class);

    private AbstractEventManager eventManager;

    @Override
    public void fire(Event event) {
        Assert.notNull(event, "event must not be null");

        eventManager.execute(event);
        if (logger.isInfoEnabled()) {
            logger.info("[fire] success. event=" + event);
        }
    }

    /**
     * @param eventManager
     *        the eventManager to set
     */
    public void setEventManager(AbstractEventManager eventManager) {
        this.eventManager = eventManager;
    }

}
