package fr.isen.tamagochi.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pet")
public record PetProperties(Duration decayInterval) {
}
