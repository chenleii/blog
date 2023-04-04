package com.chen.blog.interfaces.http.controller;

import com.chen.blog.configuration.properties.ApiProperties;
import com.chen.blog.core.account.domain.model.context.LoggedInAccount;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.interfaces.http.exception.NotLoginException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;

import jakarta.inject.Inject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author cl
 * @version 1.0
 * @since 2020/11/23 15:09
 */
@Slf4j
public abstract class AbstractAppController {

    @Getter
    @Inject
    private ApiProperties apiProperties;

    @Getter
    @Inject
    private HttpServletRequest request;
    @Getter
    @Inject
    private HttpServletResponse response;

    protected void login(LoggedInAccount account) {
        if (getApiProperties().getJwt().isEnabled()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put(LoggedInAccount.class.getSimpleName(), Serializers.json().toJsonString(account));

            Date now = new Date();
            Date expiration = DateUtils.addSeconds(now, (int) getApiProperties().getJwt().getExpirationTime().getSeconds());

            SecretKey key = Keys.hmacShaKeyFor(getApiProperties().getJwt().getSecretKeyBytes());
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setId(UUID.randomUUID().toString())
                    .setSubject(Objects.toString(account.getId()))
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(key)
                    .compact();

            // 将jwt放入harder的Authorization中（跨域访问需为header的Authorization配置cors）。
            getResponse().setHeader(HttpHeaders.AUTHORIZATION, token);
            // 方便前端不用处理jwt，将jwt放入cookie一份（注意csrf攻击风险）。
            Cookie cookie = new Cookie(HttpHeaders.AUTHORIZATION, token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            getResponse().addCookie(cookie);
        } else {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(account, null)
            );
        }
    }

    protected void logout() {
        if (getApiProperties().getJwt().isEnabled()) {
            // jwt无法登出

            Cookie cookie = new Cookie(HttpHeaders.AUTHORIZATION, null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            getResponse().addCookie(cookie);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
            SecurityContextHolder.clearContext();
        }
    }

    protected LoggedInAccount getCurrentLoggedInAccount() {
        if (getApiProperties().getJwt().isEnabled()) {
            String authorization = getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            String jwt = StringUtils.removeStart(authorization, "Bearer ");

            if (StringUtils.isBlank(jwt)) {
                Cookie[] cookies = getRequest().getCookies();
                jwt = Objects.nonNull(cookies)
                        ? Stream.of(cookies)
                        .filter((cookie -> Objects.equals(cookie.getName(), HttpHeaders.AUTHORIZATION)))
                        .map(Cookie::getValue)
                        .findFirst()
                        .orElse(null)
                        : null;
            }
            if (StringUtils.isBlank(jwt)) {
                throw new NotLoginException("not login.");
            }
            try {
                SecretKey key = Keys.hmacShaKeyFor(getApiProperties().getJwt().getSecretKeyBytes());
                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt);

                String loggedInAccountJsonString = jws.getBody().get(LoggedInAccount.class.getSimpleName(), String.class);
                return Serializers.json().fromJsonString(loggedInAccountJsonString, LoggedInAccount.class);
            } catch (Exception e) {
                throw new NotLoginException("not login.", e);
            }
        } else {
            return Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .map(Authentication::getPrincipal)
                    .filter(LoggedInAccount.class::isInstance)
                    .map(LoggedInAccount.class::cast)
                    .orElseThrow(() -> new NotLoginException("not login."));
        }
    }

    protected Long getCurrentLoggedInAccountId() {
        return getCurrentLoggedInAccount().getId();
    }

    protected boolean isLoggedIn() {
        try {
            getCurrentLoggedInAccount();
            return true;
        } catch (NotLoginException e) {
            return false;
        }
    }


}
