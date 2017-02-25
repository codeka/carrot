package au.com.codeka.carrot.lib.tag;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Statement;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.lib.Tag;

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
}
