package net.asfun.jangod.lib.macro;

import net.asfun.jangod.lib.Macro;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.TreeRebuilder;

public class BlockMacro implements Macro{

	final String MACRONAME = "block";
	final String ENDMACRONAME = "endblock";
	
	@Override
	public String getEndMacroName() {
		return ENDMACRONAME;
	}

	@Override
	public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
			throws ParseException {
		//if is parent
		if ( rebuilder.parent == null ) {
			rebuilder.assignNode(TreeRebuilder.BLK_NAME_PREFIX + helpers, current);
		} else {
			Node parentBlock = rebuilder.fetchNode(TreeRebuilder.BLK_NAME_PREFIX + helpers);
			if ( parentBlock == null ) {
				throw new ParseException("Dosen't define block in extends parent with name >>> " + helpers);
			}
//			current.replaceWithChildren(parentBlock);
			rebuilder.nodeReplaceWithChildren(parentBlock, current);
		}
	}

	@Override
	public String getName() {
		return MACRONAME;
	}

}
