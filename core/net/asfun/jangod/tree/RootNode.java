package net.asfun.jangod.tree;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;

public class RootNode extends Node{

	private static final long serialVersionUID = 97675838726004658L;

	RootNode() {
		super();
	}

	@Override
	public String render(JangodInterpreter interpreter) throws InterpretException {
		throw new UnsupportedOperationException("Please render RootNode by interpreter");
	}
	
}
