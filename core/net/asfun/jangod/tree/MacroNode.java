package net.asfun.jangod.tree;

import static net.asfun.jangod.util.logging.JangodLogger;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Macro;
import net.asfun.jangod.lib.MacroLibrary;
import net.asfun.jangod.parse.MacroToken;
import net.asfun.jangod.parse.ParseException;

public class MacroNode extends Node{

	private static final long serialVersionUID = 5037873030399458427L;
	private MacroToken master;
	String endName = null;

	public MacroNode(MacroToken token) throws ParseException{
		super();
		master = token;
		Macro macro = MacroLibrary.getMacro(master.getMacroName());
		if ( macro == null ) {
			throw new ParseException("Can't find macro >>> " + master.getMacroName());
		}
		endName = macro.getEndMacroName();
	}

	@Override
	public String render(JangodInterpreter interperter) throws InterpretException {
		JangodLogger.warning("Skiping unhandle macro while rendering >>> " + master.getMacroName());
		return "";
	}

	public void refactor(TreeRebuilder rebuilder) throws ParseException {
		Macro macro = MacroLibrary.getMacro(master.getMacroName());
		macro.refactor(this, master.getHelpers(), rebuilder);
	}

	@Override
	public String toString() {
		return master.toString();
	}
	
}
