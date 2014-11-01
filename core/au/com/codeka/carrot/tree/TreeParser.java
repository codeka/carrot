package au.com.codeka.carrot.tree;

import static au.com.codeka.carrot.parse.ParserConstants.TOKEN_ECHO;
import static au.com.codeka.carrot.parse.ParserConstants.TOKEN_FIXED;
import static au.com.codeka.carrot.parse.ParserConstants.TOKEN_MACRO;
import static au.com.codeka.carrot.parse.ParserConstants.TOKEN_NOTE;
import static au.com.codeka.carrot.parse.ParserConstants.TOKEN_TAG;
import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.parse.EchoToken;
import au.com.codeka.carrot.parse.FixedToken;
import au.com.codeka.carrot.parse.MacroToken;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TagToken;
import au.com.codeka.carrot.parse.Token;
import au.com.codeka.carrot.parse.TokenParser;

public class TreeParser {
  private Application app;

  public TreeParser(Application app) {
    this.app = app;
  }

  public Node parse(TokenParser parser) throws ParseException {
    Node root = new RootNode(app);
    tree(root, parser, RootNode.TREE_ROOT_END);
    return root;
  }

  void tree(Node node, TokenParser parser, String endName) throws ParseException {
    Token token;
    TagToken tag;
    MacroToken macro;
    while (parser.hasNext()) {
      token = parser.next();
      switch (token.getType()) {
      case TOKEN_FIXED:
        TextNode tn = new TextNode(app, (FixedToken) token);
        node.add(tn);
        break;
      case TOKEN_NOTE:
        break;
      case TOKEN_MACRO:
        macro = (MacroToken) token;
        if (macro.getMacroName().equalsIgnoreCase(endName)) {
          return;
        }
        MacroNode mn = new MacroNode(app, (MacroToken) token);
        node.add(mn);
        if (mn.endName != null) {
          tree(mn, parser, mn.endName);
        }
        break;
      case TOKEN_ECHO:
        VariableNode vn = new VariableNode(app, (EchoToken) token);
        node.add(vn);
        break;
      case TOKEN_TAG:
        tag = (TagToken) token;
        if (tag.getTagName().equalsIgnoreCase(endName)) {
          return;
        }
        TagNode tg = new TagNode(app, (TagToken) token);
        node.add(tg);
        if (tg.endName != null) {
          tree(tg, parser, tg.endName);
        }
        break;
      default:
        throw new ParseException("Unknown token: " + token);
      }
    }
    // can't reach end tag
    if (endName != null && !endName.equals(RootNode.TREE_ROOT_END)) {
      throw new ParseException("Unexpected end of file, looking for " + endName);
    }
  }
}
