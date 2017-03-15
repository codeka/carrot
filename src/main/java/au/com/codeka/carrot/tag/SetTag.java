package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.Expression;
import au.com.codeka.carrot.expr.Identifier;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.tmpl.TagNode;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Set tag allows you to set the value a variable in the current scope to the string value of the contents of the set
 * block.
 */
public class SetTag extends Tag {
  private Identifier identifier;
  @Nullable private Expression expression; // TODO: implement this.

  @Override
  public boolean isBlockTag() {
    return true;
  }

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    identifier = stmtParser.parseIdentifier();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    StringWriter stringWriter = new StringWriter();
    tagNode.renderChildren(engine, stringWriter, scope);

    scope.peek().put(identifier.evaluate(), stringWriter.toString());
  }
}
