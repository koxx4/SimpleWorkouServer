package com.koxx4.simpleworkout.simpleworkoutserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultVersionedKeyValueOperations;
import org.springframework.vault.support.Versioned;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class VaultDemo {

    private VaultEndpoint vaultEndpoint;
    private ClientAuthentication auth;

    public VaultDemo(@Autowired VaultEndpoint endpoint,
                     @Autowired ClientAuthentication auth) {

    }

    @PostConstruct
    public void run(){
        System.out.println("Starting vault demo");

        VaultTemplate vaultTemplate = new VaultTemplate(vaultEndpoint, auth);
        VaultVersionedKeyValueOperations keyValueOperations = vaultTemplate.opsForVersionedKeyValue("secret");
        Versioned<Map<String, Object>> versionedSecret = keyValueOperations.get("db/sw/main_db");

        assert versionedSecret != null;
        if(versionedSecret.hasData()){
            System.out.println((String)versionedSecret.getData().get("db_name"));
            System.out.println((String)versionedSecret.getData().get("db_username"));
            System.out.println((String)versionedSecret.getData().get("db_password"));
        }

    }
}
