package com.chen.blog.core.sharedkernel.idgenerator;

import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2020/11/2
 */
@Named
public class IdGenerators {
    private static final SnowFlakeIdGenerator SNOWFLAKE = new SnowFlakeIdGenerator();

    private static IdGenerator idGenerator;

    @Inject
    public IdGenerators(IdGenerator idGenerator) {
        IdGenerators.idGenerator = idGenerator;
    }

    public static IdGenerator current() {
        return idGenerator;
    }

    public static IdGenerator snowflake() {
        return SNOWFLAKE;
    }


}
