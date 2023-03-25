package com.chen.blog.test.bdd.stepdefinitions;

import org.apache.commons.lang3.ArrayUtils;

import jakarta.servlet.http.Cookie;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/28 12:38
 */
public class SharedStepDefinitions {

    /**
     * 共享的cookie
     * <p>
     * 默认值防止报错
     */
    private Cookie[] cookies = new Cookie[]{new Cookie("test", "t")};

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        if (ArrayUtils.isEmpty(cookies)) {
            return;
        }
        this.cookies = cookies;
    }
}
