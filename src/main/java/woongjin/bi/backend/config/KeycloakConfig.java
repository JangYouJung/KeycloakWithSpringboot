package woongjin.bi.backend.config;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class KeycloakConfig{
    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration keycloak = keycloakClientRegistration();
        return new InMemoryClientRegistrationRepository(keycloak);
    }

    private ClientRegistration keycloakClientRegistration() {

        return ClientRegistration.withRegistrationId("aws-springboot")
                .clientId("spring-security")
                .clientSecret("1DsNaaJXccDBA9oNo2tdzUi65dCVag9D")
                .redirectUri("http://localhost:8080/login/oauth2/code/keycloak")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .issuerUri("http://localhost:8090/realms/aws-springboot")
                .authorizationUri("http://localhost:8090/realms/aws-springboot/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8090/realms/aws-springboot/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8090/realms/aws-springboot/protocol/openid-connect/userinfo")
                .userNameAttributeName("preferred_username")
                .build();
    }

}
