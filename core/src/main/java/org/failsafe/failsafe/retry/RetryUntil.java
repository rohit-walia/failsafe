package org.failsafe.failsafe.retry;


import dev.failsafe.Failsafe;
import dev.failsafe.function.CheckedRunnable;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.failsafe.failsafe.common.Timeout;

/**
 * Holds useful retry functions with pre-built policies.
 */
@Slf4j
@SuppressWarnings("unchecked")
public class RetryUntil {

  /**
   * Try again until max attempts reached on any throwable exception/error.
   */
  public static void attemptLimit(@NonNull CheckedRunnable runnable, int maxAttempts) {
    attemptLimitWithDelay(runnable, maxAttempts, Timeout.ONE.getSecond());
  }

  /**
   * Try again until max attempts reached on any throwable exception/error with a delay between retry attempt.
   */
  public static void attemptLimitWithDelay(@NonNull CheckedRunnable runnable, int maxAttempts, int delayInSeconds) {
    validateParams(maxAttempts);
    var policy = new FailsafeRetryPolicy().createWithDelayAndLimit(delayInSeconds, maxAttempts);
    Failsafe.with(policy).run(runnable);
  }

  private static void validateParams(int maxAttempts) {
    if (Math.signum(maxAttempts) == -1.0) {
      throw new IllegalArgumentException("Input for maxAttempts can not be negative number.");
    }
    if (Math.signum(maxAttempts) == 0) {
      log.warn("Input for maxAttempts is 0 which is the same as not retrying at all. Executing once. Redundant call.");
    }
  }
}
