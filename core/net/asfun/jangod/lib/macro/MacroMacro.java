package net.asfun.jangod.lib.macro;

import net.asfun.jangod.lib.Macro;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.TreeRebuilder;

public class MacroMacro implements Macro{

	final String MACRONAME = "macro";
	final String ENDMACRONAME = "endmacro";
	static final String MACRO_NAME_PREFIX = "'MACRO\"NAME:";
	
	@Override
	public String getEndMacroName() {
		return ENDMACRONAME;
	}

	@Override
	public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
			throws ParseException {
		//helpers like  name  arg1=val1,arg2=val2,arg3,
		String name = "";// TODO resolve from helpers
		// TODO save param form to rebuilder
		rebuilder.assignNode(MACRO_NAME_PREFIX + name, current);
		rebuilder.nodeRemove(current);
	}

	@Override
	public String getName() {
		return MACRONAME;
	}

}
