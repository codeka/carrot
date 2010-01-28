/**********************************************************************
Copyright (c) 2009 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.jangod.node;

//import java.util.ArrayList;
//import java.util.List;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
//import net.asfun.jangod.lib.Macro;
//import net.asfun.jangod.lib.MacroLibrary;
import net.asfun.jangod.parse.MacroToken;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.parse.TokenParser;
//import net.asfun.jangod.refactor.NodeRebuilder;
//import net.asfun.jangod.refactor.RefactorException;


public class MacroNode implements Node {


//	private MacroToken master;
//	private List<Node> carries;
//	private String endMacroName;
//	private int level;
	
	private static final long serialVersionUID = 2480557554731321628L;

	public MacroNode(MacroToken token, TokenParser parser, int lvl) throws ParseException {
//		master = token;
//		Macro macro = MacroLibrary.getMacro(master.getMacroName());
//		endMacroName = macro.getEndMacroName();
//		if ( endMacroName != null ) {
//			carries = NodeParser.makeList(parser, endMacroName, level + 1);
//		} else {
//			carries = new ArrayList<Node>(0);
//		}
	}
	
//	public List<Node> refactor(NodeRebuilder rebuilder) throws RefactorException {
//		Macro macro = MacroLibrary.getMacro(master.getMacroName());
//		return macro.refactor(carries, master.getHelpers(), rebuilder);
//	}

	@Override
	public String render(JangodInterpreter interperter) throws InterpretException {
		return "";
	}

}
