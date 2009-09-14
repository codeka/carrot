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
package net.asfun.template.filter;

import javax.script.ScriptContext;
import javax.script.SimpleBindings;

import org.junit.BeforeClass;

import net.asfun.template.compile.Filter;
import net.asfun.template.compile.JangodCompiler;
import net.asfun.template.engine.JangodContext;

public class ZzzBase {

	protected static JangodCompiler compiler;
	protected Filter filter;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		SimpleBindings bindings = new SimpleBindings();
		ScriptContext context = new JangodContext(bindings);
		compiler = new JangodCompiler(context);
	}
}
