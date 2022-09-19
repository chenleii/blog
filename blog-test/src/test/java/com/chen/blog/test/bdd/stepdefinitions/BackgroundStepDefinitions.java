package com.chen.blog.test.bdd.stepdefinitions;

import lombok.Getter;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

/**
 * @author cl
 * @version 1.0
 * @since 2021/8/11 09:54
 */
@Getter
public class BackgroundStepDefinitions extends SharedStepDefinitions {


    @Inject
    private MockMvc mockMvc;
}
