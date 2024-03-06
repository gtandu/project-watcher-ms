package fr.gtandu;

import fr.gtandu.shared.core.common.config.AuditorAwareImpl;
import fr.gtandu.shared.core.common.config.FeignInterceptor;
import fr.gtandu.shared.core.common.config.PersistenceConfig;
import fr.gtandu.shared.core.common.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import({SecurityConfig.class, FeignInterceptor.class, AuditorAwareImpl.class, PersistenceConfig.class})
@SpringBootApplication
@RefreshScope
@Slf4j
@EnableFeignClients
public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
}
