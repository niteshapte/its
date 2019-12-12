package com.discovery.interstellar.transport.system.configuration;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * The Class DatasourceBean.
 */
@Configuration
public class DatasourceBean {

    /**
     * Data source.
     *
     * @return the data source
     */
    @Bean
    public DataSource dataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setConnectionAttributes("create=true");
        dataSource.setDatabaseName("InterstellarTransportSystem");
        dataSource.setUser("username");
        dataSource.setPassword("password");

        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }
}