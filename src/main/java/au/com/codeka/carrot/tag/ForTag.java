package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.*;
import au.com.codeka.carrot.bindings.Composite;
import au.com.codeka.carrot.bindings.IterableExpansionBindings;
import au.com.codeka.carrot.bindings.LoopVarBindings;
import au.com.codeka.carrot.bindings.SingletonBindings;
import au.com.codeka.carrot.expr.Identifier;
import au.com.codeka.carrot.expr.StatementParser;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TokenType;
import au.com.codeka.carrot.tmpl.Node;
import au.com.codeka.carrot.tmpl.TagNode;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * The "for" tag iterates through a loop and execute it's black for each element0.
 */
public class ForTag extends Tag {
  private List<Identifier> loopIdentifiers;
  private Term loopExpression;

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
    loopIdentifiers = stmtParser.parseIdentifierList();
    stmtParser.parseToken(TokenType.IN);
    loopExpression = stmtParser.parseTerm();
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, TagNode tagNode, Scope scope)
      throws CarrotException, IOException {

    List<Object> objects = ValueHelper.iterate(loopExpression.evaluate(engine.getConfig(), scope));
    for (int i = 0; i < objects.size(); i++) {

      Bindings loopIdentifierBindings;
      Object current = objects.get(i);
      if (loopIdentifiers.size() > 1 && current instanceof Iterable) {
        loopIdentifierBindings = new IterableExpansionBindings(loopIdentifiers, (Iterable) current);
      } else {
        loopIdentifierBindings = new SingletonBindings(loopIdentifiers.get(0).evaluate(), current);
      }

      scope.push(new Composite(
          loopIdentifierBindings,
          new SingletonBindings("loop",
              new LoopVarBindings(objects.size(), i))));
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
