package au.com.codeka.carrot.util;

import au.com.codeka.carrot.Configuration;

/**
 * Helper for writing to the configured logger.
 */
public class Log {
  public static void debug(Configuration config, String format, Object... args) {
    Configuration.Logger logger = config.getLogger();
    if (logger != null) {
      logger.print(Configuration.Logger.LEVEL_DEBUG, String.format(format, args));
    }
  }

  public static void info(Configuration config, String format, Object... args) {
    Configuration.Logger logger = config.getLogger();
    if (logger != null) {
      logger.print(Configuration.Logger.LEVEL_INFO, String.format(format, args));
    }
  }

  public static void warning(Configuration config, String format, Object... args) {
    Configuration.Logger logger = config.getLogger();
    if (logger != null) {
      logger.print(Configuration.Logger.LEVEL_WARNING, String.format(format, args));
    }
  }
}
