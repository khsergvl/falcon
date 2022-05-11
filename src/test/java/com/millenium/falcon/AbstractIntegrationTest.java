package com.millenium.falcon;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;
import org.testcontainers.utility.MountableFile;

public abstract class AbstractIntegrationTest {

    private static final BucketDefinition bucketDefinition = new BucketDefinition("testBucket");

    static final OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe:21.3.0-slim")
            .withCopyFileToContainer(MountableFile.forClasspathResource("db/init.sql"), "/container-entrypoint-startdb.d/init.sql");

    static final CouchbaseContainer couchbase = new CouchbaseContainer("couchbase/server:7.0.2")
            .withBucket(bucketDefinition);

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        //Couchbase
        registry.add("spring.couchbase.connection-string", couchbase::getConnectionString);
        registry.add("spring.couchbase.username", couchbase::getUsername);
        registry.add("spring.couchbase.password", couchbase::getPassword);
        registry.add("spring.couchbase.bucket.name", bucketDefinition::getName);

        //Oracle
        registry.add("spring.datasource.url", oracle::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "FALCON");
        registry.add("spring.datasource.password", () -> "test123");
    }

    @BeforeAll
    static void setup() {
        oracle.start();
        couchbase.start();
    }
}