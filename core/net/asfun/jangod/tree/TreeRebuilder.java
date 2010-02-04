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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.base.Configuration;
import net.asfun.jangod.base.Constants;
import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.parse.ParseException;

public class TreeRebuilder{
	
	public static final String BLK_NAME_PREFIX = "'BLK\"NAME:";
	
	String file = null;
	Application application;
	Map<String, Node> capture = new HashMap<String, Node>();
	LinkedList<Method> actions = new LinkedList<Method>();
	List<Object[]> msgs = new LinkedList<Object[]>();
	public Node parent = null;
	
	public TreeRebuilder(Application application) {
		this.application = application;
	}
	
	TreeRebuilder(Application application, String file) {
		this.application = application;
		this.file = file;
	}
	
	/**
	 * share the same capture nodes
	 * @return
	 */
	public TreeRebuilder derive() {
		TreeRebuilder tr = new TreeRebuilder(this.application, this.file);
		tr.capture = this.capture;
		tr.parent = this.parent;
		return tr;
	}
	
	public Node refactor(Node root) {
		boolean needRelevel = false;
		if ( application.isMacroOn() ) {
			TreeIterator nit = new TreeIterator(root);
			Node temp = null;
			while( nit.hasNext() ) {
				temp = nit.next();
				if ( temp instanceof MacroNode ) {
					try {
						((MacroNode)temp).refactor(this);
					} catch (ParseException e) {
						JangodLogger.warning(e.getMessage());
					}
					needRelevel = true;
				}
			}
			//real refactor must run after iterated to make sure iterator correctly
			if ( ! actions.isEmpty() ) {
				Iterator<Object[]> it = msgs.iterator();
				try {
					for(Method action : actions) {
						Object[] msg = it.next();
						Object[] param = new Object[msg.length-1];
						System.arraycopy(msg, 1, param, 0, param.length);
						action.invoke(msg[0], param);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if ( parent != null ) {
				root = parent;
			}
		}
		if ( needRelevel ) {
			root.computeLevel(0);
		}
		return root;
	}
	
	public void nodeReplaceWithChildren(Node tobe, Node withFrom) {
		try {
			Method action = Node.class.getDeclaredMethod("replaceWithChildren", new Class[]{Node.class});
			actions.add(action);
			Object[] msg = new Object[]{withFrom, tobe};
			msgs.add(msg);
		} catch (Exception e) {
			JangodLogger.severe(e.getMessage());
		}
	}
	
	public void nodeRemove(Node node) {
		try {
			Method action = Node.class.getDeclaredMethod("remove", new Class[]{});
			actions.add(action);
			Object[] msg = new Object[]{node};
			msgs.add(msg);
		} catch (Exception e) {
			JangodLogger.severe(e.getMessage());
		}
	}

	public String resolveString(String string) {
		if ( string == null || string.trim().length() == 0 ) {
			return Constants.STR_BLANK;
		}
		if ( string.startsWith(Constants.STR_DOUBLE_QUOTE) || 
				string.startsWith(Constants.STR_SINGLE_QUOTE) ) {
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
	
	public void assignNode(String key, Node node) {
		capture.put(key, node);
	}
	
	public Node fetchNode(String key) {
		return capture.get(key);
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
