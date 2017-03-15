package au.com.codeka.carrot.util;

import au.com.codeka.carrot.Configuration;

/**
 * This is a wrapper class for strings that we know are "safe" to output withing HTML-escaping. This is only useful
 * when {@link Configuration#getAutoEscape} is not true.
 */
public class SafeString {
  private final String str;

  public SafeString(String str) {
    this.str = str;
  }

  @Override
  public String toString() {
    return str;
  }
}
