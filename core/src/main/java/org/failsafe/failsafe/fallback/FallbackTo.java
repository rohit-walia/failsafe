package org.failsafe.failsafe.fallback;

import dev.failsafe.Failsafe;
import dev.failsafe.function.CheckedRunnable;

public class FallbackTo {

  /**
   * Run test steps with fallback policy to log. Useful for executing 'nice to have' test steps:
   * not critical for test case to pass, but useful for completeness.
   *
   * @param testSteps  test steps to run
   * @param logMessage log message to display if test steps fail
   */
  public static void logger(CheckedRunnable testSteps, String logMessage) {
    Failsafe.with(dev.failsafe.Fallback.of(() -> logMessage)).run(testSteps);
  }
}