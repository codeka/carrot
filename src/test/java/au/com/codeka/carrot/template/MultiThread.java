package au.com.codeka.carrot.template;

import java.util.HashMap;

import au.com.codeka.carrot.base.CarrotException;

public class MultiThread {

  static final TemplateEngine engine = new TemplateEngine();

  public static void main(String... strings) throws InterruptedException {
    R r = new R();
    for (int i = 0; i < 1; i++) {
      Thread t = new Thread(r);
      Thread.sleep(100);
      t.start();
    }
  }

  static class R implements Runnable {
    public void run() {
      HashMap<String, Object> bindings = new HashMap<String, Object>();
//      try {
//        System.out.println(engine.process("D:\\log\\index.html", bindings));
        System.out.println("=========================================");
//      } catch (CarrotException e) {
//        e.printStackTrace();
//      }
    }
  }
}
