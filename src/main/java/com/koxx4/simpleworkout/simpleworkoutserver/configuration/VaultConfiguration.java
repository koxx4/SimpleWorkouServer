package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions.RoleId;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions.SecretId;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.VaultToken;

import java.net.URI;

@Configuration
public class VaultConfiguration extends AbstractVaultConfiguration {

    @Value("${spring.cloud.vault.uri}")
    private URI vaultApiUri;
    @Value("#{systemEnvironment['INITIAL_VAULT_APPROLE_TOKEN']}")
    private String appRoleInitialToken;

    @Bean
    @Override
    public VaultEndpoint vaultEndpoint(){
        return VaultEndpoint.from(vaultApiUri);
    }

    @Bean
    @Override
    public ClientAuthentication clientAuthentication(){
        AppRoleAuthenticationOptions options = AppRoleAuthenticationOptions
                .builder()
                .appRole("simpleworkout-server-role")
                .roleId(RoleId.pull(VaultToken.of(appRoleInitialToken)))
                .secretId(SecretId.pull(VaultToken.of(appRoleInitialToken)))
                .build();
        return new AppRoleAuthentication(options, restOperations());
    }
}
