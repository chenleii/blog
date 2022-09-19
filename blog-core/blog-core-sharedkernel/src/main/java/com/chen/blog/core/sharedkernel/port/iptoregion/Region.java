package com.chen.blog.core.sharedkernel.port.iptoregion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/10 23:20
 */
@Getter
@Setter
@ToString
public class Region {

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;
}

