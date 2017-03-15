package au.com.codeka.carrot.helpers;

import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.util.SafeString;

/**
 * Registered in the global {@link Scope} as "html", contains a bunch of functions you can call from your script related
 * to HTML and HTML processing.
 */
public class HtmlHelper {
  /**
   * Mark the given string as "safe" for direct outputting.
   * @param html The string you want to mark as safe. The string will not be escaped on output, even if
   *             {@link Configuration#getAutoEscape} is true.
   * @return A {@link SafeString}.
   */
  public SafeString safe(String html) {
    return new SafeString(html);
  }

  /**
   * Escape the HTML.
   *
   * @param html The string you want to HTML-escape.
   * @return A {@link SafeString} (so that the string doesn't get double-escaped).
   */
  public SafeString escape(String html) {
    return new SafeString(ValueHelper.escape(html));
  }


  /**
   * Escape the HTML. This variant takes a {@link SafeString}, and because we already know a safe string is safe
   * to output, we <em>won't</em> escape it again.
   *
   * @param html The string you want to HTML-escape.
   * @return The {@link SafeString} you passed in.
   */
  public SafeString escape(SafeString html) {
    return html;
  }
}
