package net.asfun.jangod.tree;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.IOException;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.base.Configuration;
import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.util.logging.Level;

public class TreeRebuilder {

	String file = null;
	Application application;
	
	public TreeRebuilder(Application application) {
		this.application = application;
	}
	
	TreeRebuilder(Application application, String file) {
		this.application = application;
		this.file = file;
	}
	
	public void rebuilder(Node root) {
		boolean needRelevel = false;
		if ( application.isMacroOn() ) {
			TreeIterator nit = new TreeIterator(root);
			Node temp = null;
			try {
				while( nit.hasNext() ) {
					temp = nit.next();
					if ( temp instanceof MacroNode ) {
						((MacroNode)temp).refactor(this);
						needRelevel = true;
					}
				}
			} catch (ParseException e) {
				JangodLogger.log(Level.SEVERE, "Can't handle the macro >>> " + temp, e.getCause());
			}
		}
		if ( needRelevel ) {
			root.computeLevel(0);
		}
	}

	public String resolveString(String string) {
		if ( string == null || string.trim().length() == 0 ) {
			return "";
		}
		if ( string.startsWith("\"") || string.startsWith("'") ) {
			return string.substring(1, string.length()-1);
		}
		return string;
	}

	public String getWorkspace() {
		if ( file != null ) {
			try {
				return ResourceManager.getDirectory(file);
			} catch ( IOException e) {
				return application.getConfiguration().getWorkspace();
			}
		} else {
			return application.getConfiguration().getWorkspace();
		}
	}

	public void setFile(String fullName) {
		this.file = fullName;
	}

	public Configuration getConfiguration() {
		return application.getConfiguration();
	}

	public Application getApplication() {
		return application;
	}
}
