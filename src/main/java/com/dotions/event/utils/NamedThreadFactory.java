package com.dotions.event.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Named thread factory
 * </p>
 * Created by Dotions on 2017-10-22.
 */
public class NamedThreadFactory implements ThreadFactory {

    /**
     * Thread Serial number.
     */
    private static final AtomicLong T_SN = new AtomicLong(0);

    private static final String FIXED_PREFIX = "-pool-";

    /**
     * Thread name prefix
     */
    private String prefix;

    public NamedThreadFactory(String prefix) {
        super();
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + FIXED_PREFIX + T_SN.getAndIncrement());
    }

}
