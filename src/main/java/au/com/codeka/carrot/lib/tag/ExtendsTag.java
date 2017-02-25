package au.com.codeka.carrot.lib.tag;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.expr.Statement;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.lib.Scope;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.lib.ValueHelper;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.IOException;
import java.io.Writer;

/**
 * The "extends" tag is used to base one template off of another one.
 *
 * <p>You would make a "skeleton" template like so:
 * <code>
 *   &lt;html&gt;
 *     &lt;head&gt;
 *       &lt;title&gt;Page title&lt;/title&gt;
 *     &lt;/head&gt;
 *     &lt;body&gt;
 *       {% block "content" %}foo{% end %}
 *     &lt;/body&gt;
 *   &lt;/html&gt;
 * </code>
 *
 * <p>And another file to "extend" it, like so:
 * <code>
 *   {% extends "skeleton.html" %}
 *   {% block "content" %}
 *     bar
 *   {% end %}
 * </code>
 *
 * <p>The contents of the second file will then be the contents of the skeleton file, and the "content" block will
 * be replaced with the content inside the block.
 */
public class ExtendsTag extends Tag {
  private Statement skeletonNameStatement;

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

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    skeletonNameStatement = stmtParser.parseStatement();
  }

  @Override
  public void render(Configuration config, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    String skeletonName = skeletonNameStatement.evaluate(config, scope).toString();

  }
}
