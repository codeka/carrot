package au.com.codeka.carrot.tree;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Macro;
import au.com.codeka.carrot.lib.MacroLibrary;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.lib.TagLibrary;
import au.com.codeka.carrot.parse.MacroToken;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.util.Log;

public class MacroNode extends Node {

  private static final long serialVersionUID = 5037873030399458427L;
  private MacroToken master;
  private Log log;
  String endName = null;

  public MacroNode(Application app, MacroToken token) throws ParseException {
    super(app);
    master = token;
    log = new Log(app.getConfiguration());
    Macro macro = MacroLibrary.getMacro(master.getMacroName());
    if (macro == null) {
      throw new ParseException("Can't find macro >>> " + master.getMacroName());
    }
    endName = macro.getEndMacroName();
  }

  @Override
  public void render(JangodInterpreter interpreter, Writer writer)
      throws CarrotException, IOException {
    Tag tag = TagLibrary.getTag(master.getMacroName());
    if (tag != null) {
      log.debug("Treat macro as tag with same name: %s", master.getMacroName());
      interpreter.setLevel(level);
      tag.interpreter(children(), master.getHelpers(), interpreter, writer);
    } else {
      log.warn("Skiping handless macro while rendering: %s", master.getMacroName());
    }
  }

  public void refactor(TreeRebuilder rebuilder) throws ParseException {
    Macro macro = MacroLibrary.getMacro(master.getMacroName());
    macro.refactor(this, master.getHelpers(), rebuilder);
  }

  @Override
  public String toString() {
    return master.toString();
  }

  @Override
  public String getName() {
    return master.getMacroName();
  }

  @Override
  public Node clone() {
    try {
      Node clone = new MacroNode(app, master);
      clone.children = this.children.clone(clone);
      return clone;
    } catch (ParseException e) {
      throw new InternalError();
    }
  }
}
