package net.asfun.jangod.tree;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.parse.FixedToken;

public class TextNode extends Node{

	private static final long serialVersionUID = 8488738480534354216L;
	
	private FixedToken master;

	public TextNode(FixedToken token) {
		super();
		master = token;
	}

	@Override
	public String render(JangodInterpreter interpreter) throws InterpretException {
		return master.output();
	}

	@Override
	public String toString() {
		return master.toString();
	}
}
