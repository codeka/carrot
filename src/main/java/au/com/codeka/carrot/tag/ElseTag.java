package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.expr.Expression;
import au.com.codeka.carrot.expr.Identifier;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.tmpl.Node;
import au.com.codeka.carrot.tmpl.TagNode;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Writer;

/**
 * The {@link ElseTag} can be chained with {@link IfTag} or {@link ForTag} to contain the "else" block. In the case
 * if {@link IfTag}, it'll be chained to if the if condition is false. In the case of {@link ForTag}, it'll be chained
 * to if there are no elements in the list to be iterated.
 *
 * <p>The {@link ElseTag} can have an optional "if &lt;expr&gt;" after if, in which case the tag will basically be like
 * an {@link IfTag} that can be chained to. You can then do as many <code>{% if blah %} {% else if blah %}...{% end %}</code>
 * as you like.
 */
public class ElseTag extends Tag {
  @Nullable private Expression expr;

  @Override
  public boolean isBlockTag() {
    return true;
  }

  /**
   * Return true if we can chain to the given next {@link Tag}. If it's another else tag then we can chain to it.
   */
  public boolean canChain(Tag nextTag) {
    if (expr == null) {
      return false;
    }

    return (nextTag instanceof ElseTag);
  }

  @Override
  public void parseStatement(StatementParser stmtParser) throws CarrotException {
    Identifier identifier = stmtParser.maybeParseIdentifier();
    if (identifier != null) {
      if (!identifier.evaluate().equalsIgnoreCase("if")) {
        throw new CarrotException("Expected 'if' after 'else'.");
      }

      expr = stmtParser.parseExpression();
    }
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {
    if (expr == null || ValueHelper.isTrue(expr.evaluate(engine.getConfig(), scope))) {
      tagNode.renderChildren(engine, writer, scope);
    } else {
      Node nextNode = tagNode.getNextNode();
      if (nextNode != null) {
        nextNode.render(engine, writer, scope);
      }
    }
  }
}
