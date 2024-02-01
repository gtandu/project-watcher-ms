package fr.gtandu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
@Slf4j
public class MangasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangasApplication.class, args);
    }
}
