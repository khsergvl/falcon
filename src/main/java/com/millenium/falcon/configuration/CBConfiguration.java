package com.millenium.falcon.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

import java.sql.Date;
import java.util.Arrays;

@Configuration
@EnableReactiveCouchbaseRepositories
public class CBConfiguration extends AbstractCouchbaseConfiguration {

    @Value("${spring.couchbase.connection-string}")
    private String connection;

    @Value("${spring.couchbase.username}")
    private String userName;

    @Value("${spring.couchbase.password}")
    private String password;

    @Value("${spring.couchbase.bucket.name:GLB_RAS}")
    private String bucket;

    @Override
    public String getConnectionString() {
        return connection;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucket;
    }

    @Override
    public CustomConversions customConversions() {
        return new CouchbaseCustomConversions(Arrays.asList(SqlDateCustomConverter.INSTANCE));
    }

    @ReadingConverter
    public static enum SqlDateCustomConverter implements Converter<Long, Date> {
        INSTANCE;

        @Override
        public Date convert(Long source) {
            return new Date(source);
        }
    }
}
