package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.IOException;
import java.io.Writer;

/**
 * The "include" tag is used to include the contents of another template.
 * <p>
 * <p>Using the include tag is very simple:
 * <p>
 * <code>
 * {% include "foo.html" %}
 * </code>
 */
public class IncludeTag extends Tag {
  private Term templateNameExpr;

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    templateNameExpr = stmtParser.parseTerm();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    String templateName = templateNameExpr.evaluate(engine.getConfig(), scope).toString();

    // TODO: we should locate the resource with the current parent.
    ResourceName resourceName = engine.getConfig().getResourceLocater().findResource(null, templateName);

    // TODO: allow you to pass something specific as the new context?
    engine.process(writer, resourceName, scope);
  }
}
