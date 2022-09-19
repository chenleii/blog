package com.chen.blog.core.sharedkernel.port.iptoregion;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/10 23:11
 */
public interface IpToRegionPort {


    /**
     * ip转地区
     *
     * @param ip ip
     * @return 地区
     */
    Region ipToRegion(String ip);

}
