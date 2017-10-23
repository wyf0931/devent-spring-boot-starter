package com.dotions.event.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dotions.event.AbstractEventManager;
import com.dotions.event.DefaultEventManager;
import com.dotions.event.DefaultEventServiceImpl;
import com.dotions.event.Event;
import com.dotions.event.IEventService;
import com.dotions.event.annotation.EventListenerAnnotationProcessor;

/**
 * Created by Dotions on 2017-10-16.
 */
@Configuration
@ConditionalOnClass(Event.class)
@EnableConfigurationProperties(EventProperties.class)
public class EventAutoConfiguration {

    private final EventProperties properties;

    public EventAutoConfiguration(EventProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(IEventService.class)
    public IEventService eventService(AbstractEventManager eventManager) {
        DefaultEventServiceImpl impl = new DefaultEventServiceImpl();
        impl.setEventManager(eventManager);
        return impl;
    }

    @Bean
    @ConditionalOnMissingBean(AbstractEventManager.class)
    public AbstractEventManager eventManager() {
        DefaultEventManager eventManager = new DefaultEventManager();
        eventManager.setProperties(properties);
        return eventManager;
    }

    @Bean
    @ConditionalOnMissingBean(EventListenerAnnotationProcessor.class)
    public EventListenerAnnotationProcessor eventListenerAnnotationProcessor(AbstractEventManager eventManager) {
        EventListenerAnnotationProcessor processor = new EventListenerAnnotationProcessor();
        processor.setEventManager(eventManager);
        return processor;
    }

}
