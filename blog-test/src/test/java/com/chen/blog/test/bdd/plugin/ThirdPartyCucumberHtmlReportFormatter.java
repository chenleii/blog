package com.chen.blog.test.bdd.plugin;

import io.cucumber.core.plugin.JsonFormatter;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 生成第三方的黄瓜测试报告插件
 *
 * @author cl
 * @since 2022/03/28 12:25.
 */
public class ThirdPartyCucumberHtmlReportFormatter implements EventListener {

    /**
     * 报告输出目录
     */
    private final String reportOutputDirectory;

    /**
     * 黄瓜测试json文件
     */
    private final String cucumberJsonFile;
    private final JsonFormatter jsonFormatter;

    public ThirdPartyCucumberHtmlReportFormatter(String reportOutputDirectory) throws IOException {
        this.reportOutputDirectory = reportOutputDirectory;
        this.cucumberJsonFile = reportOutputDirectory + File.separatorChar + ReportBuilder.BASE_DIRECTORY + File.separatorChar + "cucumber.json";

        File file = new File(cucumberJsonFile);
        FileUtils.touch(file);
        this.jsonFormatter = new JsonFormatter(new FileOutputStream(file));
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        jsonFormatter.setEventPublisher(publisher);
        publisher.registerHandlerFor(TestRunFinished.class, this::finishReport);
    }

    private void finishReport(TestRunFinished event) {
        // optionally specify qualifiers for each of the report json files
        File reportOutputDirectory = new File(this.reportOutputDirectory);
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add(this.cucumberJsonFile);

        Configuration configuration = new Configuration(reportOutputDirectory, "blog");
        configuration.setBuildNumber("1");
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
        // optional configuration - check javadoc for details
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
        // do not make scenario failed when step has status SKIPPED
        configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
        // addidtional metadata presented on main page
        configuration.addClassifications("Platform", "Mac");
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0.0");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable result = reportBuilder.generateReports();
        // and here validate 'result' to decide what to do if report has failed
    }
}
