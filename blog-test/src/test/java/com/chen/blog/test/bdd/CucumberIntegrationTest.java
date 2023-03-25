package com.chen.blog.test.bdd;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,summary,html:target/cucumber/cucumber-report.html,json:target/cucumber/cucumber.json")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "true ")
@ConfigurationParameter(key = PARALLEL_CONFIG_STRATEGY_PROPERTY_NAME, value = "FIXED")
@ConfigurationParameter(key = PARALLEL_CONFIG_FIXED_PARALLELISM_PROPERTY_NAME, value = "10")
public class CucumberIntegrationTest {


    /**
     * 生成第三方的黄瓜测试报告
     * <p>
     * 要执行一次黄瓜测试后，再手动运行一次该方法才会生成报告。
     * 目前问题：在黄瓜这个{@link io.cucumber.java.AfterAll}钩子方法执行时，黄瓜测试的json文件还未持久化完成，导致报告生成失败。
     */
    @Test
    // @AfterAll(order = Integer.MAX_VALUE)
    public /*static*/ void generateReport() throws Exception {
        // optionally specify qualifiers for each of the report json files
        File reportOutputDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber/cucumber.json");

        String buildNumber = "1";
        String projectName = "blog";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
        // optional configuration - check javadoc for details
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
        // do not make scenario failed when step has status SKIPPED
        configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
        // addidtional metadata presented on main page
        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Firefox");
        configuration.addClassifications("Branch", "release/1.0");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable result = reportBuilder.generateReports();
        // and here validate 'result' to decide what to do if report has failed


    }
}
