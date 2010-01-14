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
package net.asfun.jangod.lib.tag;


import static org.junit.Assert.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


import org.junit.BeforeClass;
import org.junit.Test;

public class TagTest {
	
	static ScriptEngine engine;
	String script;
	Object res;
	
	@BeforeClass
	public static void init() {
		engine = new ScriptEngineManager().getEngineByName("Jangod");
		engine.put("var1", new Integer[]{23,45,45,689});
		engine.put("var2", "45");
		engine.put("var3", 12);
		engine.put("var5", "");
	}

	@Test
	public void forTag() throws ScriptException {
		script = "{% for item in var1 %}{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("234545689", res);
	}
	
	@Test(expected=ScriptException.class)
	public void forTag1() throws ScriptException {
		script = "{% for  %}{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("234545689", res);
	}
	
	@Test
	public void forLoop() throws ScriptException {
		script = "{% for item in var1 %}{{loop.first}}{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("true23false45false45false689", res);
	}
	
	@Test
	public void forLoop1() throws ScriptException {
		script = "{% for item in var1 %}{{loop.last}}{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("false23false45false45true689", res);
	}
	
	@Test
	public void forLoop2() throws ScriptException {
		script = "{% for item in var1 %}{{loop.index}}-{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("0-231-452-453-689", res);
	}
	
	@Test
	public void forLoop3() throws ScriptException {
		script = "{% for item in var1 %}{{loop.counter}}-{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("1-232-453-454-689", res);
	}
	
	@Test
	public void forLoop4() throws ScriptException {
		script = "{% for item in var1 %}{{loop.revindex}}-{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("3-232-451-450-689", res);
	}
	
	@Test
	public void reverseFor() throws ScriptException {
		script = "{% for item in var1|reverse %}{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("689454523", res);
	}
	
	@Test
	public void ifchangedFor() throws ScriptException {
		script = "{% for item in var1|reverse %}{%ifchanged item%}{{item}}{%endif%}{% endfor%}";
		res = engine.eval(script);
		assertEquals("6894523", res);
	}
	
	@Test
	public void cycleFor() throws ScriptException {
		script = "{% for item in var1 %}{% cycle 'a','b','c'%}{% endfor%}";
		res = engine.eval(script);
		assertEquals("abca", res);
	}
	
	@Test
	public void cycleFor0() throws ScriptException {
		script = "{% for item in var1 %}{% cycle var3,var2,'hello'%}{{item}}{% endfor%}";
		res = engine.eval(script);
		assertEquals("12234545hello4512689", res);
	}
	
	@Test
	public void cycleFor1() throws ScriptException {
		script = "{% cycle 'a','b','c' as d%}{% for item in var1 %}{%cycle d%}{% endfor%}";
		res = engine.eval(script);
		assertEquals("abca", res);
	}
	
	@Test
	public void cycleFor2() throws ScriptException {
		script = "{% cycle var3,var2,'hello' as d%}{% for item in var1 %}{%cycle d%}{% endfor%}";
		res = engine.eval(script);
		assertEquals("1245hello12", res);
	}
	
	@Test
	public void cycle() throws ScriptException {
		script = "{% cycle var3,var2,'hello'%}";
		res = engine.eval(script);
		assertEquals("12", res);
	}
	
	@Test
	public void ifTag() throws ScriptException {
		script = "{%if var1 %}hello{%endif%}";
		res = engine.eval(script);
		assertEquals("hello", res);
	}
	
	@Test
	public void ifTag0() throws ScriptException {
		script = "{%if var4 %}hello{%endif%}";
		res = engine.eval(script);
		assertEquals("", res);
	}
	
	@Test
	public void ifTag1() throws ScriptException {
		script = "{%if var5 %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("world", res);
	}
	
	@Test
	public void ifTag2() throws ScriptException {
		script = "{%if var2|equal:45 %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("hello", res);
	}
	
	@Test
	public void ifTag3() throws ScriptException {
		script = "{%if var3|equal:'12' %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("hello", res);
	}
	
	@Test
	public void ifTag4() throws ScriptException {
		script = "{%if var1|contain:var2 %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("hello", res);
	}
	
	@Test
	public void ifTag5() throws ScriptException {
		script = "{%if var1|contain:'23'|not %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("world", res);
	}
	
	@Test
	public void ifTag6() throws ScriptException {
		script = "{%if var1|and:var5,'ok' %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("world", res);
	}
	
	@Test
	public void ifTag7() throws ScriptException {
		script = "{%if var1|or:var5,'ok' %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("hello", res);
	}
	
	@Test(expected=ScriptException.class)
	public void ifTag8() throws ScriptException {
		script = "{%if  %}hello{%else%}world{%endif%}";
		res = engine.eval(script);
		assertEquals("hello", res);
	}
	
	@Test
	public void echo1() throws ScriptException {
		script = "{{ 	 }}";
		res = engine.eval(script);
		assertEquals("", res);
	}
	
	@Test
	public void echo2() throws ScriptException {
		script = "{{	_	}}";
		res = engine.eval(script);
		assertEquals("", res);
	}
	
	@Test
	public void echo3() throws ScriptException {
		script = "{{	%}	}}";
		res = engine.eval(script);
		assertEquals("", res);
	}
	
	@Test(expected=ScriptException.class)
	public void block() throws ScriptException {
		script = "{%block 	%}";
		res = engine.eval(script);
		assertEquals("", res);
	}
	
	@Test
	public void block1() throws ScriptException {
		script = "{%block a	%}";
		res = engine.eval(script);
		assertEquals("", res);
	}
}
