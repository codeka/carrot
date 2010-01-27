package net.asfun.jangod.template;

import java.io.IOException;
import java.util.HashMap;

public class MultiThread {

	static final TemplateEngine engine = new TemplateEngine();
	
	static {
		engine.application.getConfiguration().setWorkspace("D:\\workspace\\jvalog\\war\\themes\\default\\");
	}
	
	public static void main(String...strings) throws InterruptedException {
		R r = new R();
		for(int i=0; i<10; i++) {
			Thread t = new Thread(r);
			Thread.sleep(1);
			t.start();
		}
		System.out.println(">>>>>>>>>>>>>>>>>");
		Thread.sleep(3000);
		for(int i=0; i<30; i++) {
			Thread t = new Thread(r);
			Thread.sleep(12);
			t.start();
		}
		System.out.println(">>>>>>>>>>>>>>>>>");
		Thread.sleep(2000);
		for(int i=0; i<20; i++) {
			Thread t = new Thread(r);
			Thread.sleep(10);
			t.start();
		}
	}
	
	static class R implements Runnable{
		public void run(){
			HashMap<String,Object> bindings = new HashMap<String,Object>();
			try {
				System.out.println(engine.process("D:\\workspace\\jvalog\\war\\themes\\default\\index.html", bindings));
				System.out.println("=========================================");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
