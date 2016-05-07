package au.com.codeka.carrot.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import au.com.codeka.carrot.base.Configuration;

public final class Log {
  private Configuration config;

  public Log(Configuration config) {
    this.config = config;
  }

  public void write(int level, String msg) {
    config.getLogger().write(level, msg);
  }

  public void debug(String fmt, Object... args) {
    write(Configuration.Logger.DEBUG, String.format(fmt, args));
  }

  public void info(String fmt, Object... args) {
    write(Configuration.Logger.INFO, String.format(fmt, args));
  }

  public void warn(String fmt, Object... args) {
    write(Configuration.Logger.WARNING, String.format(fmt, args));
  }

  /** The default implementation of {@link Configuration.Logger} that logs to java.util.logging. */
  public static class DefaultLogger implements Configuration.Logger {
    private Logger logger = Logger.getLogger("carrot");

    @Override
    public void write(int level, String msg) {
      Level javaLevel = Level.FINE;
      switch(level) {
      case Configuration.Logger.DEBUG:
        javaLevel = Level.FINE;
        break;
      case Configuration.Logger.INFO:
        javaLevel = Level.INFO;
        break;
      case Configuration.Logger.WARNING:
        javaLevel = Level.WARNING;
        break;
      }
      logger.log(javaLevel, msg);
    }
  }
}
