package au.com.codeka.carrot.lib.tag;

import au.com.codeka.carrot.lib.Tag;

/**
 * The "end" tag, for all tags that end blocks.
 */
public class EndTag extends Tag {
  @Override
  public String getTagName() {
    return "end";
  }

  /**
   * Any string that starts with "end" is a valid end-tag ("endif", "endfor" etc). This allows you to write slightly
   * more readable templates.
   *
   * @param tagName The name of the tag we're matching.
   * @return True if the given tag name is a valid end tag.
   */
  @Override
  public boolean isMatch(String tagName) {
    return tagName.toLowerCase().startsWith("end");
  }

  @Override
  public Tag clone() {
    return new EndTag();
  }
}
