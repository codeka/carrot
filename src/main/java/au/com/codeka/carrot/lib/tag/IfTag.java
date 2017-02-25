package au.com.codeka.carrot.lib.tag;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.expr.Statement;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.lib.Scope;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.Writer;

/**
 * The "if" tag evaluates it's single parameter and outputs it's children if true. It can be chained with zero or more
 * {@link ElseifTag}s and zero or one {@link ElseTag}s.
 */
public class IfTag extends Tag {
  private Statement stmt;

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
    stmt = stmtParser.parseStatement();
  }

  @Override
  public void render(Configuration config, Writer writer, TagNode tagNode, Scope scope) throws CarrotException {
    Object value = stmt.evaluate(config, scope);

  }
}
