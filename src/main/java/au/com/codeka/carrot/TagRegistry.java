package au.com.codeka.carrot;

import au.com.codeka.carrot.tag.EchoTag;
import au.com.codeka.carrot.tag.EndTag;
import au.com.codeka.carrot.tag.IfTag;

import java.util.ArrayList;

/**
 * Contains a collection of tags that will be matched when parsing a template.
 */
public class TagRegistry {
  private final ArrayList<Tag> tags;

  public TagRegistry() {
    tags = new ArrayList<>();
    tags.add(new EchoTag());
    tags.add(new EndTag());
    tags.add(new IfTag());
  }

  public Tag createEchoTag() {
    return new EchoTag();
  }

  public Tag createTag(String tagName) {
    for (Tag tag : tags) {
      if (tag.isMatch(tagName)) {
        return tag.clone();
      }
    }

    return null;
  }
}
