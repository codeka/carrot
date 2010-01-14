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
package net.asfun.jangod.interpret;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import net.asfun.jangod.parse.JangodParser;


public class NodeListTest {

	String script;
	JangodParser parser;
	
	@Test
	public void test1() throws InterpretException {
		script = "abc{{post.title}}def";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(3, nodes.size());
	}
	
	@Test
	public void test2() throws InterpretException {
		script = "{%extends a %}abc{%block as%}{%block as.nest%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(3, nodes.size());
		assertEquals("block", nodes.get(2).toString());
	}
	
	@Test
	public void test3() throws InterpretException {
		script = "{%extends a %}abc{%block as%}{%block as.nest%}{%endblock%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(3, nodes.size());
		assertEquals("block", nodes.get(2).toString());
	}
	
	@Test
	public void test4() throws InterpretException {
		script = "{%extends a %}abc{%block as%}{%block as.nest%}{%endblock%}{%endblock%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(3, nodes.size());
		assertEquals("block", nodes.get(2).toString());
	}
	
	@Test
	public void test5() throws InterpretException {
		script = "{%extends a %}abc{%block as%}{%block as.nest%}{%endblock%}{%endblock%}{%endblock%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(3, nodes.size());
		assertEquals("block", nodes.get(2).toString());
	}
	
	@Test
	public void test6() throws InterpretException {
		script = "{%extends a %}abc{%block as%}{%block as.nest%}{%endfor%}{%endblock%}{%endblock%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(3, nodes.size());
		assertEquals("block", nodes.get(2).toString());
	}
	
	@Test
	public void test7() throws InterpretException {
		script = "{%extends a %}abc{%endend%}{%block as%}{%block as.nest%}{%endblock%}{%endblock%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(3, nodes.size());
		assertEquals("block", nodes.get(2).toString());
	}
	
	@Test
	public void test8() throws InterpretException {
		script = "{%extends a %}abc{%else%}{%block as%}{%block as.nest%}{%endblock%}{%endblock%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(4, nodes.size());
		assertEquals("else", nodes.get(2).toString());
	}
	
	@Test
	public void test9() throws InterpretException {
		script = "{%extends a %}{%if%}abc{%block as%}{%block as.nest%}{%endblock%}{%endblock%}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(2, nodes.size());
		assertEquals("if", nodes.get(1).toString());
	}
	
	@Test
	public void test10() throws InterpretException {
		script = "{%extends a %}都督府{{ab}}{#dlff{{dkf}}j#}";
		parser = new JangodParser(script);
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		assertEquals(4, nodes.size());
	}
}
