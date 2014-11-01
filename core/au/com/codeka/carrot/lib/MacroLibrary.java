package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.lib.macro.BlockMacro;
import au.com.codeka.carrot.lib.macro.ExtendsMacro;
import au.com.codeka.carrot.lib.macro.IncludeMacro;

public class MacroLibrary extends Library<Macro> {
  public MacroLibrary(Configuration config) {
    super(config, "macro");
  }

  @Override
  protected void initialize() {
    Macro incMacro = new IncludeMacro();
    register(incMacro.getName(), incMacro);
    Macro extMacro = new ExtendsMacro();
    register(extMacro.getName(), extMacro);
    Macro blkMacro = new BlockMacro();
    register(blkMacro.getName(), blkMacro);
  }

  public void register(Macro macro) {
    register(macro.getName(), macro);
  }
}
