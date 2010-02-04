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

import static net.asfun.jangod.util.logging.JangodLogger;
import net.asfun.jangod.lib.macro.BlockMacro;
import net.asfun.jangod.lib.macro.ExtendsMacro;
import net.asfun.jangod.lib.macro.IncludeMacro;

public class MacroLibrary extends SimpleLibrary<Macro>{

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
		JangodLogger.fine("Imported macro >>>" + macro.getName());
	}

}
