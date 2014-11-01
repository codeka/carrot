/**********************************************************************
Copyright (c) 2010 Asfun Net.

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
package net.asfun.jangod.tree;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.IOException;
import java.io.Writer;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Macro;
import net.asfun.jangod.lib.MacroLibrary;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.lib.TagLibrary;
import net.asfun.jangod.parse.MacroToken;
import net.asfun.jangod.parse.ParseException;

public class MacroNode extends Node {

	private static final long serialVersionUID = 5037873030399458427L;
	private MacroToken master;
	String endName = null;

	public MacroNode(Application app, MacroToken token) throws ParseException{
		super(app);
		master = token;
		Macro macro = MacroLibrary.getMacro(master.getMacroName());
		if ( macro == null ) {
			throw new ParseException("Can't find macro >>> " + master.getMacroName());
		}
		endName = macro.getEndMacroName();
	}

	@Override
	public void render(JangodInterpreter interpreter, Writer writer)
			throws InterpretException, IOException {
		Tag tag = TagLibrary.getTag(master.getMacroName());
		if ( tag != null ) {
			JangodLogger.fine("Treat macro as tag with same name >>> " + master.getMacroName());
			interpreter.setLevel(level);
			tag.interpreter(children(), master.getHelpers(), interpreter, writer);
		} else {
			JangodLogger.warning("Skiping handless macro while rendering >>> " + master.getMacroName());
		}
	}

	public void refactor(TreeRebuilder rebuilder) throws ParseException {
		Macro macro = MacroLibrary.getMacro(master.getMacroName());
		macro.refactor(this, master.getHelpers(), rebuilder);
	}

	@Override
	public String toString() {
		return master.toString();
	}
	
	@Override
	public String getName() {
		return master.getMacroName();
	}
	
	@Override
	public Node clone() {
		try {
			Node clone = new MacroNode(app, master);
			clone.children = this.children.clone(clone);
			return clone;
		} catch (ParseException e) {
			throw new InternalError();
		}
	}
}
