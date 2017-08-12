package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.tmpl.Node;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.IOException;
import java.io.Writer;

/**
 * The "if" tag evaluates it's single parameter and outputs it's children if true. It can be chained with zero or more
 * ElseifTags and zero or one ElseTags.
 */
public class IfTag extends Tag {
  private Term expr;

  @Override
  public boolean isBlockTag() {
    return true;
  }

  /**
   * Return true if we can chain to the given next {@link Tag}. If it's an else tag then we can chain to it.
   */
  public boolean canChain(Tag nextTag) {
    return (nextTag instanceof ElseTag);
  }

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    expr = stmtParser.parseTerm();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    Object value = expr.evaluate(engine.getConfig(), scope);
    if (ValueHelper.isTrue(value)) {
      tagNode.renderChildren(engine, writer, scope);
    } else {
      Node nextNode = tagNode.getNextNode();
      if (nextNode != null) {
        nextNode.render(engine, writer, scope);
      }
    }
  }
}
