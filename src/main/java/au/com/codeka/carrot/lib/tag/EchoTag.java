package au.com.codeka.carrot.lib.tag;

import au.com.codeka.carrot.lib.Tag;

/**
 * Echo tag just echos the results of it's single parameter.
 */
public class EchoTag extends Tag {
  @Override
  public String getTagName() {
    return "echo";
  }

  @Override
  public Tag clone() {
    return new EchoTag();
  }
}
