package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.tmpl.parse.Token;
import au.com.codeka.carrot.tmpl.parse.TokenType;
import au.com.codeka.carrot.tmpl.parse.Tokenizer;

/**
 * Parses a stream of {@link Token}s into a tree of {@link Node}s.
 */
public class TemplateParser {
  private final Configuration config;

  public TemplateParser(Configuration config) {
    this.config = config;
  }

  public Node parse(Tokenizer tokenizer) throws CarrotException {
    Node root = new RootNode(tokenizer.getPointer());
    parse(tokenizer, root);
    return root;
  }

  /** Parse tokens into the given {@link Node}. */
  private void parse(Tokenizer tokenizer, Node node) throws CarrotException {
    while (true) {
      Token token = tokenizer.getNextToken();
      if (token == null) {
        // Note if there's any open blocks right now, we just assume they end at the end of the file.
        return;
      }

      Node childNode;
      if (token.getType() == TokenType.COMMENT) {
        // Just ignore this token.
        childNode = null;
      } else if (token.getType() == TokenType.ECHO) {
        childNode = TagNode.createEcho(token, config);
      } else if (token.getType() == TokenType.TAG) {
        TagNode tagNode = TagNode.create(token, config);
        if (tagNode.isEndBlock()) {
          return;
        } else if (node.canChain(tagNode)) {
          // If we can chain to the given node, then instead of adding it as child, we'll chain to it instead.
          node = node.chain(tagNode);
          childNode = null;
        } else {
          childNode = tagNode;
        }
      } else if (token.getType() == TokenType.FIXED) {
        childNode = FixedNode.create(token);
      } else {
        throw new IllegalStateException("Unknown token type: " + token.getType());
      }

      if (childNode != null) {
        if (childNode.isBlockNode()) {
          parse(tokenizer, childNode);
        }
        node.add(childNode);
      }
    }
  }
}
