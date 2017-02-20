package au.com.codeka.carrot.lib.tag;

import au.com.codeka.carrot.lib.Tag;

/**
 * The "if" tag evaluates it's single parameter and outputs it's children if true. It can be chained with zero or more
 * {@link ElseifTag}s and zero or one {@link ElseTag}s.
 */
public class IfTag extends Tag {
  @Override
  public String getTagName() {
    return "if";
  }

  @Override
  public boolean isBlockTag() {
    return true;
  }

  @Override
  public Tag clone() {
    return new IfTag();
  }
}
