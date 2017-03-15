package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.expr.Expression;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.tmpl.TagNode;
import au.com.codeka.carrot.util.SafeString;

import java.io.IOException;
import java.io.Writer;

/**
 * Echo tag just echos the results of it's single parameter.
 */
public class EchoTag extends Tag {
  private Expression expr;

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    expr = stmtParser.parseExpression();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    Object value = expr.evaluate(engine.getConfig(), scope);
    if (engine.getConfig().getAutoEscape()) {
      value = ValueHelper.escape(value);
    }
    writer.write(value.toString());
  }
}
