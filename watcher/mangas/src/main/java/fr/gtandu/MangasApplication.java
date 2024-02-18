package fr.gtandu;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@RefreshScope
@Slf4j
@EnableReactiveFeignClients
public class MangasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangasApplication.class, args);
    }

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
            requestTemplate.header("Authorization", "bearer " + oauthToken.getToken().getTokenValue());
        };
    }
}
