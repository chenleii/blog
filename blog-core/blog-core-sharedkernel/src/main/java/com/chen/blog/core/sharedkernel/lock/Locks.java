package com.chen.blog.core.sharedkernel.lock;

import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/24 17:09
 */
@Named
public final class Locks {

    private static final LockFactory reentrantLockFactory = new ReentrantLockFactory(1024);

    private static LockFactory lockFactory;

    @Inject
    public Locks(LockFactory lockFactory) {
        Locks.lockFactory = lockFactory;
    }

    public static LockFactory current() {
        return lockFactory;
    }

    public static LockFactory reentrantLock() {
        return reentrantLockFactory;
    }

}
