package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Statement;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.IOException;
import java.io.Writer;

/**
 * Echo tag just echos the results of it's single parameter.
 */
public class EchoTag extends Tag {
  private Statement stmt;

  @Override
  public String getTagName() {
    return "echo";
  }

  @Override
  public Tag clone() {
    return new EchoTag();
  }

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    stmt = stmtParser.parseStatement();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    Object value = stmt.evaluate(engine.getConfig(), scope);
    writer.write(value.toString());
  }
}
