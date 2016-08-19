package org.undertree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.Instant;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class ProducerRunner implements CommandLineRunner {
  static final Logger logger = LoggerFactory.getLogger(ProducerRunner.class);

  private final KafkaTemplate<Integer, String> kafkaTemplate;

  private final WatchEvent.Kind<?>[] WATCH_EVENTS = { StandardWatchEventKinds.ENTRY_CREATE };

  @Autowired
  public ProducerRunner(KafkaTemplate<Integer, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override public void run(String... args) throws Exception {
    kafkaTemplate.send("test3-100", "start:" + Instant.now());
    fileWatcher("/tmp/files"); // s -> kafkaTemplate.send("test3-100", s));
  }

  /**
   * Watch a specific directory for file creation events, then read each line of the file
   * and publish it to a topic.
   *
   * It would be interesting to see this be able to support publishing to different topics -
   * perhaps based on name or folder...
   *
   */
  protected void fileWatcher(String path) {
    WatchKey watchKey;
    Path watchDir = Paths.get(path);

    try (WatchService watcher = watchDir.getFileSystem().newWatchService()) {
      logger.info("Start watching for file events on {}...", watchDir.toString());
      watchDir.register(watcher, WATCH_EVENTS);

      while (true) {
        watchKey = watcher.take();

        for (WatchEvent<?> event : watchKey.pollEvents()) {
          if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
            logger.warn("Some filesystem events were lost, we might have missed an event!");
            continue;
          }

          if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
            logger.info("File create event on: {}", event.context());

            WatchEvent<Path> ev = (WatchEvent<Path>) event;
            Path fileName = watchDir.resolve(ev.context());

            try (Stream<String> stream = Files.lines(fileName)) {
              stream.map(s -> s.trim())
                    .filter(s -> !s.isEmpty() && !s.startsWith("#"))
                    .forEach(s -> kafkaTemplate.send("test3-100", s));
            }
          }
        }

        // IMPORTANT: The key must be reset after processed
        if (!watchKey.reset()) {
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
