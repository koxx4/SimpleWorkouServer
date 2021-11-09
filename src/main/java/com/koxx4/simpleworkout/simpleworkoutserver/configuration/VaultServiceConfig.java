package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.koxx4.simpleworkout.simpleworkoutserver.services.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class VaultServiceConfig {

    @Autowired
    VaultConfiguration vaultConfiguration;

    @Bean
    public VaultService vaultService(){
        return new VaultService(vaultConfiguration.vaultEndpoint(),
                vaultConfiguration.clientAuthentication());
    }

}
