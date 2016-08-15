package org.undertree;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BootKafkaProducerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BootKafkaProducerApplication.class).web(false).run(args);
    }
}
