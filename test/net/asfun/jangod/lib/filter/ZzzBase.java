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
package net.asfun.jangod.lib.filter;

import javax.script.ScriptContext;
import javax.script.SimpleBindings;

import net.asfun.jangod.base.Context;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;
import net.asfun.jangod.script.JangodContext;

import org.junit.BeforeClass;


public class ZzzBase {

	protected static JangodInterpreter compiler;
	protected Filter filter;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		SimpleBindings bindings = new SimpleBindings();
		ScriptContext context = new JangodContext(bindings);
		compiler = new JangodInterpreter((Context) context);
	}
}
