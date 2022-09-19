package com.chen.blog.core.sharedkernel.lock;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * jdk的可重入锁{@link ReentrantLock}工厂
 * <p>
 * 参考{@link org.springframework.integration.support.locks.DefaultLockRegistry}
 *
 * @author cl
 * @version 1.0
 * @since 2021/6/30 20:13
 */
@Named
public class ReentrantLockFactory implements LockFactory {

    private static final int MAXIMUM_CAPACITY = 1 << 15;

    private final ReentrantLock[] reentrantLocks;

    public ReentrantLockFactory() {
        this(512);
    }

    public ReentrantLockFactory(int initialCapacity) {
        if (initialCapacity < 0) {
            initialCapacity = 1;
        }

        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }

        int capacity = tableSizeFor(initialCapacity);

        this.reentrantLocks = new ReentrantLock[capacity];
        for (int i = 0; i < reentrantLocks.length; i++) {
            reentrantLocks[i] = new ReentrantLock();
        }
    }


    /**
     * 计算容量最近大的2次方形式
     * 复制于 {@link HashMap#tableSizeFor(int)}
     */
    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Override
    public Lock create(String lockKey) {
        int hashCode = Objects.hashCode(lockKey);
        return reentrantLocks[(reentrantLocks.length - 1) & hashCode];
    }

}
