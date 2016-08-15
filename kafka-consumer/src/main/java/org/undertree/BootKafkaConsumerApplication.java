package org.undertree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BootKafkaConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BootKafkaConsumerApplication.class).web(false).run(args);
    }
}
