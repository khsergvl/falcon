package com.millenium.falcon;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.ToxiproxyContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractIntegrationTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    private static final DockerImageName TOXIPROXY_IMAGE = DockerImageName.parse("shopify/toxiproxy:2.1.0");

    static final Network network = Network.newNetwork();

    static final GenericContainer mysql = new MySQLContainer()
            .withInitScript("db/init.sql")
            .withExposedPorts(3306)
            .withNetwork(network);

    static final ToxiproxyContainer toxiproxy = new ToxiproxyContainer(TOXIPROXY_IMAGE)
            .withNetwork(network)
            .withNetworkAliases("toxiproxy");

    static ToxiproxyContainer.ContainerProxy proxy;


    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        proxy =  toxiproxy.getProxy(mysql, (Integer) mysql.getExposedPorts().get(0));

        //Mysql
        String url = String.format("jdbc:mysql://%s:%s/test", proxy.getContainerIpAddress(), proxy.getProxyPort());
        LOGGER.info("connection url is {}", url);
        registry.add("spring.datasource.url", () -> url);
        registry.add("spring.datasource.username", () -> ((MySQLContainer)mysql).getUsername());
        registry.add("spring.datasource.password", () -> ((MySQLContainer)mysql).getPassword());
    }

    @BeforeAll
    static void setup() {
        toxiproxy.start();
        mysql.start();
    }
}