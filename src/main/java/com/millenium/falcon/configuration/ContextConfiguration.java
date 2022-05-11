package com.millenium.falcon.configuration;

import oracle.jdbc.datasource.impl.OracleDataSource;
import oracle.jms.AQjmsFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableJms
public class ContextConfiguration {

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${queue.oracle.receiveTimeout}")
    private Long receiveTimeout;

    @Value("${queue.oracle.maxMessagesPerTask}")
    private Integer maxMessagesPerTask;

    @Bean("oracleDatasource")
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        return dataSource;
    }

    @Bean(name ="AQjmsFactory")
    public QueueConnectionFactory connectionFactory(DataSource oracleDatasource) throws Exception {
        return AQjmsFactory.getQueueConnectionFactory(oracleDatasource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("AQjmsFactory") ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean(name = "JMSListenerFactory")
    public JmsListenerContainerFactory<?> JMSListenerFactory(QueueConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer, PlatformTransactionManager transactionManager) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setTransactionManager(transactionManager);
        factory.setMaxMessagesPerTask(maxMessagesPerTask);
        factory.setSessionTransacted(true);
        factory.setReceiveTimeout(receiveTimeout);
        return factory;
    }
}


