package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.lib.Scope;

import java.io.IOException;
import java.io.Writer;

/**
 * A {@link FixedNode} represents the text outside of the {% ... %} tags: the text that's just "fixed".
 */
public class FixedNode extends Node {
  private String content;

  private FixedNode(String content) {
    super(false /* isBlockNode */);
    this.content = content;
  }

  public static FixedNode create(String content) {
    return new FixedNode(content);
  }

  public String getContent() {
    return content;
  }

  @Override
  public void render(Configuration config, Writer writer, Scope scope) throws IOException {
    writer.write(content);
  }
}
