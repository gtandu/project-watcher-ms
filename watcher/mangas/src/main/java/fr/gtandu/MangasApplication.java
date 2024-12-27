package fr.gtandu;

import fr.gtandu.common.config.AuditorAwareImpl;
import fr.gtandu.common.config.PersistenceConfig;
import fr.gtandu.common.config.properties.WatcherApiProperties;
import fr.gtandu.common.config.security.SecurityConfig;
import fr.gtandu.common.config.security.properties.JwtAuthConverterProperties;
import fr.gtandu.mangadex.config.MangaDexApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@RefreshScope
@Slf4j
@Import({SecurityConfig.class, AuditorAwareImpl.class, PersistenceConfig.class})
@EnableConfigurationProperties({WatcherApiProperties.class, MangaDexApiProperties.class, JwtAuthConverterProperties.class})
public class MangasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangasApplication.class, args);
    }
}
