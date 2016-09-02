package org.undertree;

import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class ConsumerListener {

  @KafkaListener(topics = "topic3-100")
  public void listen(List<String> message) //, Acknowledgment ack)
                     //, @Header(KafkaHeaders.OFFSET) Long offset,
                     //@Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition
  {
    System.out.println("Batch = " + message.size());
    message.stream()
           .map(s -> String.format("Received [%-20s][%d]: %s", Thread.currentThread().getName(), 0, s))
           .forEach(System.out::println);

    //ack.acknowledge();
  }
}
