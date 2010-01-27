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

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.parse.InstToken;
import net.asfun.jangod.parse.ParseException;

//import java.util.List;

public class InstNode implements Node {


//	private InstToken token;
//	private Instruction inst;
//	private List<Node> carries;
//	private String endInstName;
	
	private static final long serialVersionUID = 2480557554731321628L;

	public InstNode(InstToken token) throws ParseException {
//		token = tk;
//		inst = InstructionLibrary.getInstruction(token.getInstName());
//		endInstName = inst.getEndInstName();
//		if ( endInstName != null ) {
//			carries = NodeList.makeList(parser, endInstName, level + 1);
//		} else {
//			return
//		}
	}

	@Override
	public String render(JangodInterpreter interperter) throws InterpretException {
		return "";
	}

}
