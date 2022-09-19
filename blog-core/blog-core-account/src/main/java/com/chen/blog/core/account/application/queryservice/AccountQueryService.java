package com.chen.blog.core.account.application.queryservice;

import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 17:59
 */
@QueryService
@Slf4j
@Named
@Validated
public class AccountQueryService {
}
