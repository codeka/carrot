package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.resource.ResourcePointer;
import au.com.codeka.carrot.tmpl.parse.Token;

import java.io.IOException;
import java.io.Writer;

/**
 * A {@link FixedNode} represents the text outside of the {% ... %} tags: the text that's just "fixed".
 */
public class FixedNode extends Node {
  private String content;

  private FixedNode(ResourcePointer ptr, String content) {
    super(ptr, false /* isBlockNode */);
    this.content = content;
  }

  public static FixedNode create(Token token) {
    return new FixedNode(token.getPointer(), token.getContent());
  }

  public String getContent() {
    return content;
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, Scope scope) throws IOException {
    writer.write(content);
  }
}
