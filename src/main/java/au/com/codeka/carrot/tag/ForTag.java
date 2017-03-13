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

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The "for" tag iterates through a loop and execute it's black for each element0.
 */
public class ForTag extends Tag {
  private Identifier loopIdentifier;
  private Expression loopExpression;

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
    loopIdentifier = stmtParser.parseIdentifier();
    Identifier inIdentifier = stmtParser.parseIdentifier();
    if (!inIdentifier.evaluate().equalsIgnoreCase("in")) {
      throw new CarrotException("Expected 'in'.");
    }
    loopExpression = stmtParser.parseExpression();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {

    List<Object> objects = ValueHelper.iterate(loopExpression.evaluate(engine.getConfig(), scope));
    Map<String, Object> loop = new HashMap<>();
    for (int i = 0; i < objects.size(); i++) {
      Map<String, Object> context = new HashMap<>();
      context.put(loopIdentifier.evaluate(), objects.get(i));

      loop.put("index", i);
      loop.put("revindex", objects.size() - i - 1);
      loop.put("first", i == 0);
      loop.put("last", i == (objects.size() - 1));
      loop.put("length", objects.size());
      context.put("loop", loop);

      scope.push(context);
      tagNode.renderChildren(engine, writer, scope);
      scope.pop();
    }

    // If we have an else block and the collection was empty, render the else instead.
    if (objects.size() == 0) {
      Node nextNode = tagNode.getNextNode();
      if (nextNode != null) {
        nextNode.render(engine, writer, scope);
      }
    }
  }
}
