package com.dotions.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.dotions.event.configuration.EventProperties;
import com.dotions.event.utils.ListenerWrapper;
import com.dotions.event.utils.NamedThreadFactory;
import com.dotions.event.utils.Utils;

/**
 * 事件管理器
 * Created by Dotions on 2017-10-22.
 */
public abstract class AbstractEventManager {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEventManager.class);

    private static final String EVENT_TYPE_PREFIX = "E_T_";

    /** Event type ———> Event handler list. */
    protected Map<String, List<ListenerWrapper>> eventHandlerMap = new HashMap<>();

    /** 待执行的事件 **/
    protected LinkedBlockingQueue<Runnable> eventQueue;
    protected NamedThreadFactory factory;
    private ThreadPoolExecutor executor;

    protected EventProperties properties;

    private AtomicBoolean initFlag = new AtomicBoolean(false);

    /**
     * 用的时候再初始化
     */
    protected synchronized void lazyInit() {
        Assert.notNull(properties, "properties must not be null.");

        int size = properties.getThreadPoolSize();
        String threadPrefix = properties.getThreadNamePrefix();

        // 初始化线程工厂
        factory = new NamedThreadFactory(threadPrefix);
        // 初始化待执行事件线程队列
        eventQueue = new LinkedBlockingQueue<>();
        // 初始化一个固定大小的 executor
        executor = new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, eventQueue, factory);
        initFlag.set(true);

        if(logger.isInfoEnabled()) {
            logger.info("[lazyInit] initialize success.");
        }
    }

    protected void execute(Event event) {
        // 判断是否已经初始化
        if (!initFlag.get()) {
            lazyInit();
        }

        List<ListenerWrapper> listeners = getListeners(event.getType());
        if (listeners.isEmpty()) {
            logger.warn("[execute] Not found listener, event=" + event);
            return;
        }

        EventContext context = new EventContext(event, listeners);
        executor.execute(() -> {
            long ts = System.currentTimeMillis();
            executeInternal(context);
            if (logger.isDebugEnabled()) {
                logger.debug("[execute] eventType=" + event.getType() + ", cost=" + Utils.cost(ts));
            }
        });
    }

    /**
     * 自定义事件执行过程
     */
    protected abstract void executeInternal(EventContext context);

    /**
     * 添加监听
     */
    public synchronized void registerListener(int eventType, int order, Listener listener) {
        ListenerWrapper lw = new ListenerWrapper(listener, order, eventType);
        List<ListenerWrapper> list = getListeners(eventType);
        if (list.isEmpty()) {
            list = new ArrayList<>();
        }
        list.add(lw);
        eventHandlerMap.put(buildEventTypeName(eventType), list);
    }

    /**
     * 获取监听该类型事件的监听器
     * 
     * @param eventType 事件类型
     * @return
     */
    public List<ListenerWrapper> getListeners(int eventType) {
        String eventTypeString = buildEventTypeName(eventType);
        List<ListenerWrapper> list = eventHandlerMap.get(eventTypeString);
        return list == null ? Collections.emptyList() : list;
    }

    private static final String buildEventTypeName(int type) {
        return EVENT_TYPE_PREFIX + type;
    }

    public void setProperties(EventProperties properties) {
        this.properties = properties;
    }

}
