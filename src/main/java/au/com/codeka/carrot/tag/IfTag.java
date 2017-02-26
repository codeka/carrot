package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.*;
import au.com.codeka.carrot.expr.Statement;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.IOException;
import java.io.Writer;

/**
 * The "if" tag evaluates it's single parameter and outputs it's children if true. It can be chained with zero or more
 * {@link ElseifTag}s and zero or one {@link ElseTag}s.
 */
public class IfTag extends Tag {
  private Statement stmt;

  @Override
  public boolean isBlockTag() {
    return true;
  }

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    stmt = stmtParser.parseStatement();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    Object value = stmt.evaluate(engine.getConfig(), scope);
    if (ValueHelper.isTrue(value)) {
      tagNode.renderChildren(engine, writer, scope);
    }
  }
}
