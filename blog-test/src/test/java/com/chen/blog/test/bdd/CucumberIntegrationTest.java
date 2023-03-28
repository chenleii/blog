package com.chen.blog.test.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * 黄瓜测试主入口
 *
 * @author cl
 * @since 2020/11/15 2:25.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/chen/blog/test/bdd/")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.chen.blog.test.bdd")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty" +
        ",summary" +
        ",html:target/cucumber/cucumber-report.html" +
        ",json:target/cucumber/cucumber.json" +
        ",com.chen.blog.test.bdd.plugin.ThirdPartyCucumberHtmlReportFormatter:target/cucumber/cucumber-report")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "true ")
@ConfigurationParameter(key = PARALLEL_CONFIG_STRATEGY_PROPERTY_NAME, value = "FIXED")
@ConfigurationParameter(key = PARALLEL_CONFIG_FIXED_PARALLELISM_PROPERTY_NAME, value = "10")
public class CucumberIntegrationTest {

}
