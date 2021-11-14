package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.koxx4.simpleworkout.simpleworkoutserver.data.vault.DatabaseCredentials;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.VaultKeyValueSecretException;
import com.koxx4.simpleworkout.simpleworkoutserver.services.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Autowired
    VaultService vaultService;

    @Bean
    public DataSource dataSource() throws VaultKeyValueSecretException {
        DatabaseCredentials databaseCredentials = vaultService.getDatabaseCredentials();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(String.format("jdbc:mariadb://%s:3306/%s",
                databaseCredentials.getAddress(), databaseCredentials.getDbName()));
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUsername(databaseCredentials.getUsername());
		dataSource.setPassword(databaseCredentials.getPassword());

        return dataSource;
    }

}
