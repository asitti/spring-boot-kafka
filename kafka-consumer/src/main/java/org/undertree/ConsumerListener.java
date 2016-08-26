package org.undertree;

import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class ConsumerListener {

  @KafkaListener(topics = "test3-100")
  public void listen(List<String> message) {
    message.stream()
           .map(s -> "Received: " + s)
           .forEach(System.out::println);
  }
}
