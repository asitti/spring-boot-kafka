package org.undertree;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by ss65560 on 8/15/16.
 */
@Component
public class ConsumerListener {

  @KafkaListener(topics = "test3-100")
  public void listen(String foo) {
    System.out.println("Recieved: " + foo);
  }
}
