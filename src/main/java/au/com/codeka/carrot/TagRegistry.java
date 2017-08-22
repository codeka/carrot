package au.com.codeka.carrot;

import au.com.codeka.carrot.tag.*;
import au.com.codeka.carrot.util.Log;

import java.util.ArrayList;

/**
 * Contains a collection of tags that will be matched when parsing a template.
 */
public class TagRegistry {
  /** Interface to implement for custom tag matching. */
  public interface TagMatcher {
    boolean isMatch(String tagName);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  private final Configuration config;
  private final ArrayList<Entry> entries;

  private TagRegistry(Configuration config, ArrayList<Entry> entries) {
    this.config = config;
    this.entries = entries;
  }

  public Tag createTag(String tagName) {
    for (Entry entry : entries) {
      if (entry.matcher.isMatch(tagName)) {
        try {
          return entry.tagClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
          Log.warning(config, "Error creating instance of tag '%s': %s", tagName, e);
        }
      }
    }

    return null;
  }

  private static class Entry {
    TagMatcher matcher;
    Class<? extends Tag> tagClass;

    public Entry(TagMatcher matcher, Class<? extends Tag> tagClass) {
      this.matcher = matcher;
      this.tagClass = tagClass;
    }
  }

  private static class DefaultTagMatcher implements TagMatcher {
    private final String tagName;

    public DefaultTagMatcher(String tagName) {
      this.tagName = tagName;
    }

    @Override
    public boolean isMatch(String tagName) {
      return tagName.equalsIgnoreCase(this.tagName);
    }
  }

  public static class Builder {
    private final ArrayList<Entry> entries = new ArrayList<>();

    public Builder() {
      add("echo", EchoTag.class);
      add("if", IfTag.class);
      add("for", ForTag.class);
      add("else", ElseTag.class);
      add("extends", ExtendsTag.class);
      add("block", BlockTag.class);
      add("set", SetTag.class);
      add("include", IncludeTag.class);
      add(new TagMatcher() {
        @Override
        public boolean isMatch(String tagName) {
          return tagName.toLowerCase().startsWith("end");
        }
      }, EndTag.class);
    }

    public Builder add(String name, Class<? extends Tag> tagClass) {
      return add(new DefaultTagMatcher(name), tagClass);
    }

    public Builder add(TagMatcher tagMatcher, Class<? extends Tag> tagClass) {
      entries.add(new Entry(tagMatcher, tagClass));
      return this;
    }

    public TagRegistry build(Configuration config) {
      return new TagRegistry(config, entries);
    }
  }
}
