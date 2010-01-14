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
package net.asfun.jangod.parse;

import static net.asfun.jangod.parse.ParserConstants.*;
import static org.junit.Assert.*;

//import org.junit.Ignore;
import org.junit.Test;



public class JangodParserTest {

	JangodParser parser;
	String script;
	
	@Test
	public void test1() throws ParseException {
		script = "{{abc.b}}{% if x %}{\\{abc}}{%endif%}";
		parser = new JangodParser(script);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("{{abc}}", parser.next().content);
		assertEquals("{%endif%}", parser.next().image);
	}
	
	@Test
	public void test2() throws ParseException {
		script = "{{abc.b}}{% if x %}{{abc{%endif";
		parser = new JangodParser(script);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("{{abc{%endif", parser.next().content);
	}
	
	@Test
	public void test3() throws ParseException {
		script = "{{abc.b}}{% if x %}{{{abc}}{%endif%}";
		parser = new JangodParser(script);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		Token tk = parser.next();
		assertEquals("{{{abc}}", tk.image);
		assertEquals(TOKEN_ECHO, tk.getType());
		assertEquals("{%endif%}", parser.next().image);
	}
	
	@Test
	public void test4() throws ParseException {
		script = "{{abc.b}}{% if x %}{{!abc}}{%endif%}";
		parser = new JangodParser(script);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		Token tk = parser.next();
		assertEquals("{{!abc}}", tk.image);
		assertEquals(TOKEN_ECHO, tk.getType());
		assertEquals("{%endif%}", parser.next().image);
	}
	
	@Test
	public void test5() throws ParseException {
		script = "{{abc.b}}{% if x %}a{{abc}\\}{%endif%}";
		parser = new JangodParser(script);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc}\\}{%endif%}", parser.next().content);
	}
	
	@Test
	public void test6() throws ParseException {
		script = "a{{abc.b}}{% if x 	%}a{\\{abc}}{%endif%}";
		parser = new JangodParser(script);
		assertEquals("a", parser.next().image);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a{{abc}}", parser.next().content);
		assertEquals("{%endif%}", parser.next().image);
	}
	
	@Test
	public void test7() throws ParseException {
		script = "a{{abc.b}}{% if x 	%}a{{abc!}#}%}}}{%endif";
		parser = new JangodParser(script);
		assertEquals("a", parser.next().image);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc!}#}%}}", parser.next().image);
		assertEquals("}", parser.next().content);
		assertEquals(TOKEN_FIXED, parser.next().getType());
	}
	
	@Test
	public void test8() throws ParseException {
		script = "a{{abc.b}}{% if x 	%}a{{abc}}{%endif{{";
		parser = new JangodParser(script);
		assertEquals("a", parser.next().image);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals(TOKEN_ECHO, parser.next().getType());
		assertEquals("{%endif{{", parser.next().content);
	}
	
	@Test
	public void test9() throws ParseException {
		script = "a{{abc.b}}{% if x 	%}a{{abc}\\}{%endif{";
		parser = new JangodParser(script);
		assertEquals("a", parser.next().image);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals(TOKEN_FIXED, parser.next().getType());
	}
	
	@Test
	public void test10() throws ParseException {
		script = "a{{abc.b}}{% if x %}a{{abc}\\}{{#%endif{";
		parser = new JangodParser(script);
		assertEquals("a", parser.next().image);
		assertEquals("{{abc.b}}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc}\\}{", parser.next().image);
		assertEquals(TOKEN_NOTE, parser.next().getType());
	}
	
	@Test
	public void test11() throws ParseException {
		script = "a{#abc.b#}{% if x %}a{{abc}\\}{{{{#endif{";
		parser = new JangodParser(script);
		assertEquals("a", parser.next().image);
		assertEquals("{#abc.b#}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc}\\}{{{", parser.next().content);
		assertEquals("{#endif{", parser.next().image);
	}
	
	@Test
	public void test12() throws ParseException {
		script = "{#abc.b#}{% if x %}a{{abc}\\}{{{{#endif{";
		parser = new JangodParser(script);
		assertEquals("{#abc.b#}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc}\\}{{{", parser.next().content);
		assertEquals(TOKEN_NOTE, parser.next().getType());
	}
	
	@Test
	public void test13() throws ParseException {
		script = "{#abc{#.b#}{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}#}{% if x %}a{{abc}\\}{{{{#endif{";
		parser = new JangodParser(script);
		assertEquals("{#abc{#.b#}{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}#}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc}\\}{{{", parser.next().content);
		assertEquals(TOKEN_NOTE, parser.next().getType());
	}
	
	@Test
	public void test14() throws ParseException {
		script = "abc{#.b#}{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}#}{% if x %}a{{abc}\\}{{{{#endif{";
		parser = new JangodParser(script);
		assertEquals("abc", parser.next().image);
		assertEquals("{#.b#}", parser.next().image);
		assertEquals("{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}", parser.next().image);
		Token tk = parser.next();
		assertEquals("#}", tk.image);
		assertEquals(TOKEN_FIXED, tk.getType());
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc}\\}{{{", parser.next().content);
		assertEquals(TOKEN_NOTE, parser.next().getType());
	}
	
	@Test
	public void test15() throws ParseException {
		script = "abc{#.b#}{#xy{!ad!}{#DD#}{%dbc%}{{dff}}d{#bc#}d#}#}{% if x %}a{{abc}\\}{{{{#endif{";
		parser = new JangodParser(script);
		assertEquals("abc", parser.next().image);
		assertEquals("{#.b#}", parser.next().image);
		assertEquals("{#xy{!ad!}{#DD#}{%dbc%}{{dff}}d{#bc#}d#}", parser.next().image);
		Token tk = parser.next();
		assertEquals("#}", tk.image);
		assertEquals(TOKEN_FIXED, tk.getType());
		assertEquals("if x", parser.next().content);
		assertEquals("a", parser.next().content);
		assertEquals("{{abc}\\}{{{", parser.next().content);
		assertEquals(TOKEN_NOTE, parser.next().getType());
	}
	
	@Test
	public void test16() throws ParseException {
		script = "{#{#abc{#.b#}{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}#}{% if x %}a{{abc}\\}{{{{#endif{";
		parser = new JangodParser(script);
		assertEquals("{#{#abc{#.b#}{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}#}{% if x %}a{{abc}\\}{{{{#endif{", parser.next().image);
	}
	
	@Test
	public void test17() throws ParseException {
		script = "{#abc{#.b#}{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}#}{% if x %}#}a#}{{abc}\\}#}{{{{#endif{";
		parser = new JangodParser(script);
		assertEquals("{#abc{#.b#}{#xy{!ad!}{%dbc%}{{dff}}d{#bc#}d#}#}", parser.next().image);
		assertEquals("if x", parser.next().content);
		assertEquals("#}a#}", parser.next().content);
		assertEquals("{{abc}\\}#}{{{", parser.next().content);
		assertEquals(TOKEN_NOTE, parser.next().getType());
	}
}
