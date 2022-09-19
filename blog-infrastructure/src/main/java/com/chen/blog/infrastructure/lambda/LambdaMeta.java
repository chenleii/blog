package com.chen.blog.infrastructure.lambda;

import lombok.*;

/**
 * lambda元信息
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/11 19:05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class LambdaMeta {


    /**
     * lambda 表达式实现的类
     */
    private Class<?> implClass;

    /**
     * lambda 表达式实现方法的名称
     */
    private String implMethodName;

}