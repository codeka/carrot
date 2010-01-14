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
package net.asfun.jangod.lib;

//import net.asfun.template.inst.*;
import static net.asfun.jangod.util.logging.JangodLogger;

public class InstructionLibrary extends SimpleLibrary<Instruction>{

	private static InstructionLibrary lib;
	
	static {
		lib = new InstructionLibrary();
	}
	
	@Override
	protected void initialize() {
		//TODO someday
	}
	
	public static Instruction getInstruction(String name) {
		return lib.fetch(name);
	}
	
	public static void addInstruction(Instruction inst) {
		lib.register(inst.getName(), inst);
		JangodLogger.fine("Imported instruction >>>" + inst.getName());
	}

}
