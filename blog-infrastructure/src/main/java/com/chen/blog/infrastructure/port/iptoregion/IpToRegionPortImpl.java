package com.chen.blog.infrastructure.port.iptoregion;

import com.chen.blog.core.sharedkernel.port.iptoregion.IpToRegionPort;
import com.chen.blog.core.sharedkernel.port.iptoregion.Region;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import jakarta.inject.Named;
import java.io.IOException;

/**
 * 文档：https://gitee.com/lionsoul/ip2region/tree/master/binding/java
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 23:11
 */
@Named
@Slf4j
public class IpToRegionPortImpl implements IpToRegionPort {

    private Searcher searcher;

    @PostConstruct
    public void init() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        byte[] ipToRegionXdbBytes = IOUtils.resourceToByteArray("iptoregion/ip2region.xdb", classLoader);
        this.searcher = Searcher.newWithBuffer(ipToRegionXdbBytes);
    }

    @PreDestroy
    public void destroy() throws IOException {
        searcher.close();
    }


    @SneakyThrows
    @Override
    public Region ipToRegion(String ip) {
        String regionString = searcher.search(ip);

        String[] regionSplit = StringUtils.split(regionString, "|");

        Region region = new Region();
        region.setCountry(ArrayUtils.get(regionSplit, 0));
        region.setProvince(ArrayUtils.get(regionSplit,2));
        region.setCity(ArrayUtils.get(regionSplit,3));
        return region;
    }

}
