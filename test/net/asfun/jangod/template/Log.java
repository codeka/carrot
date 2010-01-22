package net.asfun.jangod.template;

import java.io.FileWriter;
import java.io.IOException;

public class Log {

	private FileWriter f;
	
	public Log() {
		try {
			f = new FileWriter("D:\\log\\" + Thread.currentThread().getId() + ".log", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void append(String msg) {
		try {
			f.append(msg + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
