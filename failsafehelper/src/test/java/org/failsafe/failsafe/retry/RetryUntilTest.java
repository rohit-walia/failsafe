package org.failsafe.failsafe.retry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;

class RetryUntilTest {

  @Test
  void tryUntil_withMinAttempts() {
    var list = new ArrayList<String>();
    RetryUntil.attemptLimit(() -> list.add("test"), 0);
    Assertions.assertEquals(1, list.size());
    Assertions.assertEquals(List.of("test"), list);

    RetryUntil.attemptLimitWithDelay(() -> list.add("test"), 0, 1);
    Assertions.assertEquals(2, list.size());
    Assertions.assertEquals(List.of("test", "test"), list);
  }

  @Test
  void tryUntil_withNegativeAttempts_shouldThrowException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> RetryUntil.attemptLimit(() -> {
    }, -1));
    Assertions.assertThrows(IllegalArgumentException.class, () -> RetryUntil.attemptLimitWithDelay(() -> {
    }, -1, 1));
  }

  @Test
  void tryUntil_WhenErrorOnFirstAttempt() {
    var list = new ArrayList<String>();
    final var retryAttempts = 2;

    RetryUntil.attemptLimit(() -> {
      list.add("test");

      // this mimics runtime exception occurring on the first attempt and succeeding on the second
      if (list.size() < retryAttempts) {
        Assertions.fail();
      }
    }, retryAttempts);

    Assertions.assertEquals(retryAttempts, list.size());
    Assertions.assertEquals(List.of("test", "test"), list);

    RetryUntil.attemptLimitWithDelay(() -> {
      list.add("test");

      // this mimics runtime exception occurring on the first attempt and succeeding on the second
      if (list.size() < retryAttempts * 2) {
        Assertions.fail();
      }
    }, retryAttempts, 1);

    Assertions.assertEquals(retryAttempts * 2, list.size());
    Assertions.assertEquals(List.of("test", "test", "test", "test"), list);
  }

  @Test
  void tryUntil_WhenErrorOnAllAttempts() {
    Assertions.assertThrows(AssertionFailedError.class, () -> RetryUntil.attemptLimit(Assertions::fail, 3));
    Assertions.assertThrows(AssertionFailedError.class, () -> RetryUntil.attemptLimitWithDelay(Assertions::fail, 1, 1));
  }
}