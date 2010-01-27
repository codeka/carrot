package net.asfun.jangod.base;


import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FileLocaterTest {

	FileLocater locater;
	
	@Before
	public void setUp() throws Exception {
		locater = new FileLocater();
	}
	
	@Test
	public void test1() throws IOException {
		assertEquals("E:\\books", locater.getDirectory("E:\\books"));
	}

	@Test
	public void test2() throws IOException {
		assertEquals("E:\\books", locater.getDirectory("E:\\books\\PEAA.chm"));
	}
	
	@Test
	public void test3() throws IOException {
		assertEquals("D:\\", locater.getDirectory("d:/"));
	}
	
	@Test
	public void test4() throws IOException {
		assertEquals("D:\\", locater.getDirectory("d:\\"));
	}
	
	@Test
	public void test5() throws IOException {
		assertEquals("D:\\", locater.getDirectory("D:\\1.txt"));
	}
	
	@Test
	public void test6() throws IOException {
		assertEquals("E:\\", locater.getDirectory("E:\\123.txt"));
	}
}
