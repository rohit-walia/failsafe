package org.failsafe.failsafe.retry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;

class RetryAgainTest {

  @Test
  void tryAgain_withoutError() {
    var list = new ArrayList<String>();
    RetryAgain.once(() -> list.add("test"));
    Assertions.assertEquals(1, list.size());
    Assertions.assertEquals(List.of("test"), list);
  }

  @Test
  void tryAgain_WhenErrorOnFirstAttempt() {
    var list = new ArrayList<String>();
    final var retryAttempts = 2;

    RetryAgain.once(() -> {
      list.add("test");

      // this mimics runtime exception occurring on the first attempt and succeeding on the second
      if (list.size() < retryAttempts) {
        Assertions.fail();
      }
    });

    Assertions.assertEquals(retryAttempts, list.size());
    Assertions.assertEquals(List.of("test", "test"), list);
  }

  @Test
  void tryAgain_WhenErrorOnAllAttempts() {
    Assertions.assertThrows(AssertionFailedError.class, () -> RetryAgain.once(Assertions::fail));
  }
}