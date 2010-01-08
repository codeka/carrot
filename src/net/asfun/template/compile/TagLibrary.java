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
package net.asfun.template.compile;

import static net.asfun.template.util.logging.JangodLogger;
import net.asfun.template.tag.*;

public class TagLibrary extends SimpleLibrary<Tag>{
	
	private static TagLibrary lib;
	
	static {
		lib = new TagLibrary();
	}

	@Override
	protected void initialize() {
		Tag extTag = new ExtendsTag();
		register(extTag.getName(), extTag);
		Tag blkTag = new BlockTag();
		register(blkTag.getName(), blkTag);
		Tag incTag = new IncludeTag();
		register(incTag.getName(), incTag);
		
		Tag forTag = new ForTag();
		register(forTag.getName(), forTag);
		Tag cycleTag = new CycleTag();
		register(cycleTag.getName(), cycleTag);
//		Tag ifcTag = new IfchangedTag();
//		register(ifcTag.getName(), ifcTag);
		
		Tag ifTag = new IfTag();
		register(ifTag.getName(), ifTag);	
		Tag elseTag = new ElseTag();
		register(elseTag.getName(), elseTag);
	}

	public static Tag getTag(String tagName) throws CompilerException {
		return lib.fetch(tagName);
	}

	public static void addTag(Tag tag) {
		lib.register(tag.getName(), tag);
		JangodLogger.fine("Imported tag >>>" + tag.getName());
	}
}
