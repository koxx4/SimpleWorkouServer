package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.vault.DatabaseCredentials;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.VaultKeyValueSecretException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultVersionedKeyValueOperations;
import org.springframework.vault.support.Versioned;

import java.util.Map;

@Profile(value = "production")
@Component
@DependsOn("vaultConfiguration")
public class VaultService {

    private final VaultEndpoint vaultEndpoint;
    private final ClientAuthentication auth;


    @Autowired
    public VaultService(VaultEndpoint endpoint,
                        ClientAuthentication auth) {

        this.vaultEndpoint = endpoint;
        this.auth = auth;
    }


    public DatabaseCredentials getDatabaseCredentials() throws VaultKeyValueSecretException {

        VaultTemplate vaultTemplate = new VaultTemplate(vaultEndpoint, auth);
        VaultVersionedKeyValueOperations keyValueOperations = vaultTemplate.opsForVersionedKeyValue("secret");
        Versioned<Map<String, Object>> versionedSecret = keyValueOperations.get("db/sw/main_db");

        if (versionedSecret == null || !versionedSecret.hasData())
            throw new VaultKeyValueSecretException("Couldn't get database credentials! Suitable key value pair was not found.");

        DatabaseCredentials databaseCredentials = new DatabaseCredentials();
        databaseCredentials.setDbName(String.valueOf(versionedSecret.getData().get("db_name")));
        databaseCredentials.setUsername(String.valueOf(versionedSecret.getData().get("db_username")));
        databaseCredentials.setPassword(String.valueOf(versionedSecret.getData().get("db_password")));
        databaseCredentials.setAddress(String.valueOf(versionedSecret.getData().get("db_address")));

        return databaseCredentials;
    }
}
