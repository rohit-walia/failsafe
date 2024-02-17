package org.failsafe.failsafe.retry;

import dev.failsafe.Failsafe;
import dev.failsafe.function.CheckedRunnable;
import lombok.NonNull;
import org.failsafe.failsafe.common.Timeout;

@SuppressWarnings("unchecked")
public class RetryAgain {
  /**
   * Try again once and only once on any throwable exception/error. There is a default 1-second delay between retry attempt.
   */
  public static void once(@NonNull CheckedRunnable runnable) {
    onceWithDelay(runnable, Timeout.ONE.getSecond());
  }

  /**
   * Try again once and only once on any throwable exception/error with a delay between retry attempt.
   */
  public static void onceWithDelay(@NonNull CheckedRunnable runnable, int delayInSeconds) {
    var policy = new FailsafeRetryPolicy().createWithDelayAndLimit(delayInSeconds, 1);
    Failsafe.with(policy).run(runnable);
  }
}
