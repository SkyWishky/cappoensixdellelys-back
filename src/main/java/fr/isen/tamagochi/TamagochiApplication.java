package fr.isen.tamagochi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TamagochiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TamagochiApplication.class, args);
    }
}
