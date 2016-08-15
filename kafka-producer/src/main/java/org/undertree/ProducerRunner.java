package org.undertree;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class ProducerRunner implements CommandLineRunner {
  private final KafkaTemplate<Integer, String> kafkaTemplate;

  @Autowired
  public ProducerRunner(KafkaTemplate<Integer, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override public void run(String... args) throws Exception {
    kafkaTemplate.send("test3-100", "foo" + Instant.now());
  }
}
