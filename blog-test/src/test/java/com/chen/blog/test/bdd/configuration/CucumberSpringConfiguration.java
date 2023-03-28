package com.chen.blog.test.bdd.configuration;

import com.chen.blog.test.TestApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * @author cl
 * @since 2020/11/15 2:14.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = TestApplication.class)
@Transactional
@AutoConfigureMockMvc
@Testcontainers
public class CucumberSpringConfiguration {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:5.0.11-focal"))
            .withExposedPorts(27017);
    @Container
    static final ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(
            DockerImageName.parse("elasticsearch:7.17.5")
                    .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch"))
            .withClasspathResourceMapping("es/", "/usr/share/elasticsearch/plugins/", BindMode.READ_ONLY)
            .withExposedPorts(9200);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        elasticsearchContainer.start();

        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);

        registry.add("spring.elasticsearch.uris", () -> "http://" + elasticsearchContainer.getHost() + ":" + elasticsearchContainer.getFirstMappedPort());
    }

}
