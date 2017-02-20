package au.com.codeka.carrot.tree;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.lib.Scope;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.lib.tag.EndTag;
import au.com.codeka.carrot.parse.Token;

import java.io.IOException;
import java.io.Writer;

/**
 * A {@link TagNode} represents a node of the form "{% tagname foo %}" where "tagname" is the name of the tag and "foo"
 * is the parameters.
 *
 * <p>Tags are represented by the {@link Tag} class, which is extensible.
 */
public class TagNode extends Node {
  private final Tag tag;

  public TagNode(Tag tag, String content) {
    super(tag.isBlockTag() /* isBlockNode */);
    this.tag = tag;
  }

  /** Creates a special {@link TagNode} for an echo token. */
  public static TagNode createEcho(Token token, Configuration config) throws CarrotException {
    Tag tag = config.getTagRegistry().createEchoTag();
    return new TagNode(tag, token.getContent());
  }

  public static TagNode create(Token token, Configuration config) throws CarrotException {
    String content = token.getContent().trim();

    String tagName;
    int space = content.indexOf(' ');
    if (space <= 0) {
      tagName = content;
    } else {
      tagName = content.substring(0, space);
      content = content.substring(space).trim();
    }
    Tag tag = config.getTagRegistry().createTag(tagName);
    if (tag == null) {
      throw new CarrotException(String.format("Invalid tag '%s'", tagName), token.getLine(), token.getColumn());
    }

    return new TagNode(tag, content);
  }

  public boolean isEndBlock() {
    return tag instanceof EndTag;
  }

  public Tag getTag() {
    return tag;
  }

  @Override
  public void render(Writer writer, Scope scope) throws IOException {
    // TODO
  }
}
