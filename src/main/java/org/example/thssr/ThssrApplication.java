package org.example.thssr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ThssrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThssrApplication.class, args);
    }

}
