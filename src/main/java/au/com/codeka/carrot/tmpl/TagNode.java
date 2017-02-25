package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.expr.Tokenizer;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.Tag;
import au.com.codeka.carrot.tag.EndTag;
import au.com.codeka.carrot.tmpl.parse.Token;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

/**
 * A {@link TagNode} represents a node of the form "{% tagname foo %}" where "tagname" is the name of the tag and "foo"
 * is the parameters.
 *
 * <p>Tags are represented by the {@link Tag} class, which is extensible.
 */
public class TagNode extends Node {
  private final Tag tag;

  public TagNode(Tag tag) {
    super(tag.isBlockTag() /* isBlockNode */);
    this.tag = tag;
  }

  /** Creates a special {@link TagNode} for an echo token. */
  public static TagNode createEcho(Token token, Configuration config) throws CarrotException {
    return create("echo", token.getContent(), config, token.getLine(), token.getColumn());
  }

  public static TagNode create(Token token, Configuration config) throws CarrotException {
    String content = token.getContent().trim();

    String tagName;
    int space = content.indexOf(' ');
    if (space <= 0) {
      tagName = content;
      content = "";
    } else {
      tagName = content.substring(0, space);
      content = content.substring(space).trim();
    }

    return create(tagName, content, config, token.getLine(), token.getColumn());
  }

  private static TagNode create(
      String tagName, String content, Configuration config, int line, int col) throws CarrotException {
    Tag tag = config.getTagRegistry().createTag(tagName);
    if (tag == null) {
      throw new CarrotException(String.format("Invalid tag '%s'", tagName), line, col);
    }

    try {
      StatementParser stmtParser = new StatementParser(new Tokenizer(new StringReader(content)));
      tag.parseStatement(stmtParser);
    } catch (CarrotException e) {
      throw new CarrotException("Exception parsing statement for '" + tagName + "' [" + content + "]", e);
    }

    return new TagNode(tag);
  }

  public boolean isEndBlock() {
    return tag instanceof EndTag;
  }

  public Tag getTag() {
    return tag;
  }

  @Override
  public void render(Configuration config, Writer writer, Scope scope) throws CarrotException, IOException {
    tag.render(config, writer, this, scope);
  }
}
