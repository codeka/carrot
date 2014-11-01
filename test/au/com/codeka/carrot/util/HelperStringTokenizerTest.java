package au.com.codeka.carrot.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelperStringTokenizerTest {

  private HelperStringTokenizer tk;

  @Test
  public void test1() {
    tk = new HelperStringTokenizer("111,222 333 '444', 		555");
    tk.next();
    tk.next();
    assertEquals("'444',", tk.next());
  }

  @Test
  public void test2() {
    tk = new HelperStringTokenizer("111,222 333 '444', 		555");
    tk.next();
    tk.next();
    tk.next();
    assertEquals("555", tk.next());
  }

  @Test
  public void test3() {
    tk = new HelperStringTokenizer("111,222 333 '444', 		555");
    tk.splitComma(true);
    tk.next();
    tk.next();
    tk.next();
    assertEquals("'444'", tk.next());
  }

  @Test
  public void test4() {
    tk = new HelperStringTokenizer("111,222 333 '444', 		555");
    tk.splitComma(true);
    tk.next();
    tk.next();
    tk.next();
    tk.next();
    assertEquals("555", tk.next());
  }

  @Test
  public void test5() {
    tk = new HelperStringTokenizer("111,', \"' \"222\"' 	;, ' 333 '444', 		555");
    tk.splitComma(true);
    tk.next();
    assertEquals("', \"'", tk.next());
  }

  @Test
  public void test6() {
    tk = new HelperStringTokenizer("111,', \"' \"222\"' 	;, ' 333 '444', 		555");
    tk.splitComma(true);
    tk.next();
    tk.next();
    tk.next();
    assertEquals("333", tk.next());
  }

  @Test
  public void test7() {
    tk = new HelperStringTokenizer("111,', \"' \"222\"' 	;, ' 333 '444', 		555");
    tk.splitComma(true);
    tk.next();
    tk.next();
    assertEquals("\"222\"' 	;, '", tk.next());
  }

  @Test
  public void test8() {
    tk = new HelperStringTokenizer(
        "111 ', \"' \"222\"' 	;, ' 333 post.id|add:'444',\"555\",666 		555");
    tk.next();
    tk.next();
    tk.next();
    tk.next();
    assertEquals("post.id|add:'444',\"555\",666", tk.next());
  }
}
