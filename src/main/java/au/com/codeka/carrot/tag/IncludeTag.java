package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.bindings.EmptyBindings;
import au.com.codeka.carrot.bindings.IterableExpansionBindings;
import au.com.codeka.carrot.bindings.SingletonBindings;
import au.com.codeka.carrot.expr.Identifier;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TokenType;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tmpl.TagNode;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static au.com.codeka.carrot.util.Preconditions.checkNotNull;

/**
 * The "include" tag is used to include the contents of another template.
 *
 * <p>Using the include tag is very simple:
 *
 * <pre><code>
 * {% include "foo.html" %}
 * </code></pre>
 */
public class IncludeTag extends Tag {
  private Term templateNameExpr;
  @Nullable private List<Identifier> identifiers;
  @Nullable private Term expression;

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    templateNameExpr = stmtParser.parseTerm();

    identifiers = stmtParser.maybeParseIdentifierList();
    if (identifiers != null) {
      stmtParser.parseToken(TokenType.ASSIGNMENT);
      expression = stmtParser.parseTerm();
    }
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    String templateName = templateNameExpr.evaluate(engine.getConfig(), scope).toString();

    // TODO: we should locate the resource with the current parent.
    ResourceName resourceName = engine.getConfig().getResourceLocator().findResource(null, templateName);

    if (identifiers != null && identifiers.size() == 1) {
      checkNotNull(expression);
      String identifier = checkNotNull(identifiers.get(0).evaluate());
      scope.push(
          new SingletonBindings(
              identifier,
              expression.evaluate(engine.getConfig(), scope)));
    } else if (identifiers != null) {
      checkNotNull(expression);
      scope.push(
          new IterableExpansionBindings(
              identifiers,
              evaluateIterable(expression, engine, scope)));
    } else {
      scope.push(new EmptyBindings());
    }

    engine.process(writer, resourceName, scope);

    scope.pop();
  }

  @SuppressWarnings("unchecked")
  private Iterable<Object> evaluateIterable(Term expression, CarrotEngine engine, Scope scope) throws CarrotException {
    return (Iterable<Object>) expression.evaluate(engine.getConfig(), scope);
  }
}
