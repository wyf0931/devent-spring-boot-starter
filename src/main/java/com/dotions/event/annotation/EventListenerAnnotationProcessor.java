package com.dotions.event.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import com.dotions.event.AbstractEventManager;
import com.dotions.event.Listener;
import com.dotions.event.utils.Utils;

/**
 * <p>
 * {@code EventListener} annotation processor.
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class EventListenerAnnotationProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EventListenerAnnotationProcessor.class);

    private AbstractEventManager eventManager;

    /**
     * @param eventManager
     *        the eventManager to set
     */
    public void setEventManager(AbstractEventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 扫描 EventListener 注解
        scanEventListenerAnnotation(bean);
        return bean;
    }

    /**
     * 扫描 EventListener 注解并注册 listener 对象
     */
    protected void scanEventListenerAnnotation(Object bean) {
        if (null == bean) {
            return;
        }

        if (bean instanceof Listener) {
            Listener listener = (Listener) bean;

            EventListener anno = AnnotatedElementUtils.findMergedAnnotation(listener.getClass(), EventListener.class);
            int eventType = anno.eventType();

            try {
                eventManager.registerListener(eventType, anno.order(), listener);
                if (logger.isInfoEnabled()) {
                    logger.info("[Scan-EventListener] found listener. eventType=" + eventType + ", listener="
                            + Utils.getClassName(listener));
                }
            } catch (Exception e) {
                logger.error(
                        "[Scan-EventListener] scan fail. eventType=" + eventType + ", listener=" + Utils.getClassName(listener),
                        e);
            }
        }
    }

}
