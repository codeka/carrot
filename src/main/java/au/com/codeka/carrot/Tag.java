package au.com.codeka.carrot;

import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.IOException;
import java.io.Writer;

/**
 * Interface that tags must implement.
 *
 * <p>A tag is a keyword that appears at the beginning of a {% %} block. The tag must have a single-word, lower-case
 * name. If the tag can itself contain content, it must specify the name of the end-tag which ends the tag.</p>
 */
public abstract class Tag implements Cloneable {
  /** Gets the name of this tag. */
  public abstract String getTagName();

  /**
   * The default implementation of {@link #isMatch} just checks that the name is the same as returned from
   * {@link #getTagName()}. This method can be overridden to provide more nuanced matching.
   */
  public boolean isMatch(String tagName) {
    return tagName.equalsIgnoreCase(getTagName());
  }

  /**
   * @return True if this is a "block" tag, meaning it contains child content (in the form of a list of {@link Node}s)
   *         and false if this is not a block tag (e.g. it's just a single inline element or something).
   */
  public boolean isBlockTag() {
    return false;
  }

  /**
   * Parse the statement that appears after the tag in the markup.
   * @param stmtParser A {@link StatementParser} for parsing the statement.
   * @throws CarrotException if there is an unrecoverable error parsing the statement.
   */
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    stmtParser.parseEnd();
  }

  /**
   * Render this {@link Tag} to the given {@link Writer}.
   *
   * @param config The current {@link Configuration}.
   * @param writer The {@link Writer} to render to.
   * @param tagNode The {@link TagNode} that we're enclosed in. You can use this to render the children, or query
   *                the children or whatever.
   * @param scope The current {@link Scope}.
   */
  public void render(Configuration config, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
  }

  @Override
  public abstract Tag clone();
}
