package au.com.codeka.carrot.tree;

import static au.com.codeka.carrot.parse.ParserConstants.*;
import static au.com.codeka.carrot.util.logging.JangodLogger;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.parse.EchoToken;
import au.com.codeka.carrot.parse.FixedToken;
import au.com.codeka.carrot.parse.MacroToken;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TagToken;
import au.com.codeka.carrot.parse.Token;
import au.com.codeka.carrot.parse.TokenParser;
import au.com.codeka.carrot.util.logging.Level;

public class TreeParser {
  private Application app;

  public TreeParser(Application app) {
    this.app = app;
  }

  public Node parse(TokenParser parser) {
    Node root = new RootNode(app);
    tree(root, parser, RootNode.TREE_ROOT_END);
    return root;
  }

  void tree(Node node, TokenParser parser, String endName) {
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
        try {
          MacroNode mn = new MacroNode(app, (MacroToken) token);
          node.add(mn);
          if (mn.endName != null) {
            tree(mn, parser, mn.endName);
          }
        } catch (ParseException e) {
          JangodLogger
              .log(Level.WARNING, "can't create node with token >>> " + token, e.getCause());
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
        try {
          TagNode tg = new TagNode(app, (TagToken) token);
          node.add(tg);
          if (tg.endName != null) {
            tree(tg, parser, tg.endName);
          }
        } catch (ParseException e) {
          JangodLogger
              .log(Level.WARNING, "can't create node with token >>> " + token, e.getCause());
        }
        break;
      default:
        JangodLogger.warning("unknown type token >>> " + token);
      }
    }
    // can't reach end tag
    if (endName != null && !endName.equals(RootNode.TREE_ROOT_END)) {
      JangodLogger.severe("lost end for tag or macro >>> " + endName);
    }
  }
}
