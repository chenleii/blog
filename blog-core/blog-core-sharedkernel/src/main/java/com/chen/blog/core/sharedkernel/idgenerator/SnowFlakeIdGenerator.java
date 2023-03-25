package com.chen.blog.core.sharedkernel.idgenerator;

import lombok.SneakyThrows;

import jakarta.inject.Named;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2020/11/2
 */
@Named
public class SnowFlakeIdGenerator implements IdGenerator {

    private final SnowFlake snowFlake;

    public SnowFlakeIdGenerator() {
        this.snowFlake = new SnowFlake(getDataCenterId(), getMachineId());
    }

    public SnowFlakeIdGenerator(long dataCenterId, long machineId) {
        this.snowFlake = new SnowFlake(dataCenterId, machineId);
    }


    @SneakyThrows
    private long getDataCenterId() {
        // JVM的pid
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Math.max(Objects.hashCode(name) % 32, 1);
    }

    @SneakyThrows
    private long getMachineId() {
        // 当前机器ip地址
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        return Math.max(Objects.hashCode(hostAddress) % 32, 1);
    }

    @Override
    public long generateId() {
        return this.snowFlake.nextId();
    }
}
