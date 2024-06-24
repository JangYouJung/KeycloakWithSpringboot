package woongjin.bi.backend.config;


import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@KeycloakConfiguration
public class KeycloakSecurityConfig {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resource-server.jwt.issuer-uri}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.redirect-uri}")
    private String redirectUrl;


    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement((sessionManagement) -> 얘가 오류의 원인이었다!
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests.requestMatchers("/public").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
        ;


        return http.build();
    }


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration keycloakRegistration = ClientRegistration.withRegistrationId("keycloak")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationUri(baseUrl +"/protocol/openid-connect/auth")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(redirectUrl)
                .tokenUri( baseUrl + "/protocol/openid-connect/token")
                .userInfoUri( baseUrl + "/protocol/openid-connect/userinfo")
                .jwkSetUri(baseUrl + "/protocol/openid-connect/certs")
                .userNameAttributeName("preferred_username")
                .build();

        System.out.println(keycloakRegistration.toString());

        return new InMemoryClientRegistrationRepository(keycloakRegistration);
    }

}
