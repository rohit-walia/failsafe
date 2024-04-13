package org.failsafe.failsafe.fallback;

import dev.failsafe.Fallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FallbackPolicy {
  /**
   * Creates fallback policy.
   *
   * @param logMessage message to log
   * @return Fallback obj
   * @see <a href="https://failsafe.dev/fallback/">official FailSafe documentation</a>
   */
  public Fallback<Object> fallbackPolicyToLog(String logMessage) {
    return Fallback.of(() -> {
      log.info(logMessage);
    });
  }
}
