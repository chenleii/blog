package com.chen.blog.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import jakarta.inject.Named;
import java.time.Duration;
import java.util.Base64;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/11 12:31
 */
@Getter
@Setter
@ToString
@Named
@EnableConfigurationProperties(ApiProperties.class)
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    /**
     * api文档配置
     */
    @NestedConfigurationProperty
    private DocProperties doc = new DocProperties();

    /**
     * api的jwt配置
     */
    @NestedConfigurationProperty
    private JwtProperties jwt = new JwtProperties();


    @Getter
    @Setter
    @ToString
    public static class DocProperties {

        /**
         * 是否开启
         */
        private boolean enabled;
    }

    @Getter
    @Setter
    @ToString
    public static class JwtProperties {

        /**
         * 是否开启
         */
        private boolean enabled;

        /**
         * 密钥
         */
        private String secretKey;


        /**
         * 密钥的字节数组形式
         */
        private byte[] secretKeyBytes;

        /**
         * 过期时长
         */
        private Duration expirationTime;

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;

            this.secretKeyBytes = Base64.getDecoder().decode(secretKey);
        }
    }
}
