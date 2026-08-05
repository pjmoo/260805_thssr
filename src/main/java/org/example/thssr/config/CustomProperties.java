package org.example.thssr.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "custom")
@Validated
public record CustomProperties(
        @NotBlank String name, @NotBlank String title) {
}
