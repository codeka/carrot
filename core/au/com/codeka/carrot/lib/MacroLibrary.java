package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.lib.macro.BlockMacro;
import au.com.codeka.carrot.lib.macro.ExtendsMacro;
import au.com.codeka.carrot.lib.macro.IncludeMacro;

public class MacroLibrary extends SimpleLibrary<Macro> {
  private static MacroLibrary lib;

  static {
    lib = new MacroLibrary();
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

  public static Macro getMacro(String name) {
    return lib.fetch(name);
  }

  public static void addMacro(Macro macro) {
    lib.register(macro.getName(), macro);
    //JangodLogger.fine("Imported macro >>>" + macro.getName());
  }
}
