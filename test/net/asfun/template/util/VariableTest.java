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
package net.asfun.template.util;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VariableTest {

	private Object obj;
	private Variable var;
	private Object res;
	
	@Before
	public void setUp() throws Exception {
		obj = new java.util.Date();
	}
	
	@Test
	public void test1() {
		var = new Variable("now.DAte");
		res = var.resolve(obj);
		assertEquals(9,res);
	}

}
