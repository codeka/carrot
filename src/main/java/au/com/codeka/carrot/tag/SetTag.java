package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.bindings.IterableExpansionBindings;
import au.com.codeka.carrot.bindings.SingletonBindings;
import au.com.codeka.carrot.expr.Identifier;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.tmpl.TagNode;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * Set tag allows you to set the value a variable in the current scope to the string value of the contents of the set
 * block.
 */
public class SetTag extends Tag {
  private List<Identifier> identifiers;
  @Nullable
  private Term expression;

  @Override
  public boolean isBlockTag() {
    return expression == null;
  }

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    identifiers = stmtParser.parseIdentifierList();

    if (stmtParser.isAssignment()) {
      expression = stmtParser.parseTerm();
    } else if (identifiers.size() != 1) {
      throw new CarrotException("Block assignment does not support unpacking.");
    }
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {

    if (expression == null) {
      StringWriter stringWriter = new StringWriter();
      tagNode.renderChildren(engine, stringWriter, scope);

      scope.extendCurrent(new SingletonBindings(identifiers.get(0).evaluate(), stringWriter.toString()));
    } else if (identifiers.size() == 1) {
      scope.extendCurrent(new SingletonBindings(identifiers.get(0).evaluate(), expression.evaluate(engine.getConfig(), scope)));
    } else {
      scope.extendCurrent(new IterableExpansionBindings(identifiers, (Iterable<Object>) expression.evaluate(engine.getConfig(), scope)));
    }
  }
}
