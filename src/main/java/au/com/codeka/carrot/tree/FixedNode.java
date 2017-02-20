package au.com.codeka.carrot.tree;

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
}
