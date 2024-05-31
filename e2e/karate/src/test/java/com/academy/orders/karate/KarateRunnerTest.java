package com.academy.orders.karate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

public class KarateRunnerTest {

  @Test
  void triggerKarate() {
    var results = Runner.builder()
        .path("classpath:apis")
        .parallel(1);

   assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }
}
