package fr.gtandu;

import fr.gtandu.common.config.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@RefreshScope
@Slf4j
@Import({SecurityConfig.class, FeignInterceptor.class, AuditorAwareImpl.class, PersistenceConfig.class})
@EnableConfigurationProperties(WatcherApiProperties.class)
public class MangasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangasApplication.class, args);
    }
}
