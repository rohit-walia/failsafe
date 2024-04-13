package org.failsafe.failsafe.retry;

import dev.failsafe.RetryPolicy;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
public class FailsafeRetryPolicy {
  /**
   * Creates retry policy.At minimum, you must supply the delayInSeconds and maxAttempts parameters. To control which
   * exceptions are retried, you can supply a list of exceptions. If no exceptions are supplied, all exceptions will trigger
   * a retry attempt.
   *
   * @param delayInSeconds delay between retry attempts
   * @param maxAttempts    max attempts to retry before giving up
   * @param exceptions     exceptions to handle as failures
   * @return RetryPolicy obj
   * @see <a href="https://failsafe.dev/retry/">official FailSafe documentation</a>
   */
  public final RetryPolicy<Object> createWithDelayAndLimit(int delayInSeconds, int maxAttempts,
                                                           Class<? extends Throwable>... exceptions) {
    if (exceptions.length == 0) {
      exceptions = List.of(Throwable.class).toArray(exceptions);
    }

    return RetryPolicy.builder()
        .handle(exceptions)
        .withDelay(Duration.ofSeconds(delayInSeconds))
        .withMaxRetries(maxAttempts)
        .onRetry(e -> log.info("Failed on #{} retry! Error: {}. Retrying...", e.getAttemptCount(),
            e.getLastException().getMessage().lines().toList().get(1)))
        .onRetriesExceeded(e -> log.error("Max attempts reached.", e.getException()))
        .build();
  }
}
