package com.dotions.event.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.dotions.event.AbstractEventManager;
import com.dotions.event.Listener;

/**
 * <p>
 * {@code EventListener} annotation processor.
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class EventListenerAnnotationProcessor implements BeanPostProcessor {

	private AbstractEventManager eventManager;

	/**
	 * @param eventManager
	 *            the eventManager to set
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
		// System.out.println("[scan] class=" + bean.getClass().getName());
		scanEventListenerAnnotation(bean, beanName);
		return bean;
	}

	protected void scanEventListenerAnnotation(Object bean, String beanName) {
		if (bean instanceof Listener) {
			Listener listener = (Listener) bean;

			Class<?> generic = listener.getClass();
			EventListener eventListenerAnno = generic.getDeclaredAnnotation(EventListener.class);
			int eventType = eventListenerAnno.value();
			int order = eventListenerAnno.order();
			eventManager.addListener(eventType, order, listener);
			System.err.println("[scan] add listener, eventType=" + eventType + ", class=" + generic.getName());
		} else {
			System.out.println("[scan] skip bean, class=" + bean.getClass().getName());
		}
	}

}
