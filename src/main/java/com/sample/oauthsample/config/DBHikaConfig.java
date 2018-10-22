//package com.sample.oauthsample.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//@EnableJpaRepositories("com.sample.oauthsample.repository")
//@EntityScan("com.sample.oauthsample.model")
//@EnableTransactionManagement
//public class DBHikaConfig {
//
//    /**
//     * Public constructor.
//     */
//    public DBHikaConfig() {
//
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    public HikariConfig hikariConfig() {
//        return new HikariConfig();
//    }
//
//    @Primary
//    @Bean(destroyMethod = "close")
//    public DataSource dataSource() {
//        return new HikariDataSource(hikariConfig());
//    }
//}
