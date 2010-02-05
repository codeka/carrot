package net.asfun.jangod.lib.macro;

import net.asfun.jangod.lib.Macro;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.parse.TagToken;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.TagNode;
import net.asfun.jangod.tree.TreeRebuilder;

public class CallMacro implements Macro{

	final String MACRONAME = "call";
	
	@Override
	public String getEndMacroName() {
		return null;
	}

	@Override
	public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
			throws ParseException {
		//helpers like  name  arg2=val2,arg3=var3
		String name = "";// TODO get from helpers;
		Node defineNode = rebuilder.fetchNode(MacroMacro.MACRO_NAME_PREFIX + name);
		if ( defineNode == null ) {
			throw new ParseException("Call a macro didn't define yet >>> " + name);
		}
		String[] args = new String[]{};// TODO resolve from macro's helpers
		String[] vals = new String[]{};// TODO resolve from helpers and macro's helpers
		for (int i=0; i<args.length; i++ ) {
			TagNode tn = new TagNode(new TagToken("{%set "+args[i] + " " + vals[i] + " just %}"));
			rebuilder.nodeInsertBefore(current, tn);
		}
		rebuilder.nodeReplace(current, defineNode.clone().children());
	}

	@Override
	public String getName() {
		return MACRONAME;
	}

}
