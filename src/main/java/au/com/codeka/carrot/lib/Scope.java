package au.com.codeka.carrot.lib;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Scope is a collection of all the bindings that are active at the given node that's being rendered.
 */
public class Scope {
  private final Deque<Map<String, Object>> stack = new ArrayDeque<>();

  public Scope(Map<String, Object> globalBindings) {
    stack.add(globalBindings);
  }

  /** Push the given bindings onto the stack. */
  public void push(Map<String, Object> bindings) {
    stack.push(bindings);
  }

  /** Pop the most recent bindings off the stack. */
  public void pop() {
    stack.pop();
  }

  /** Resolve the given named variable from the stack of bindings, most recently-pushed to last. */
  @Nullable
  public Object resolve(String name) {
    for (Map<String, Object> bindings : stack) {
      Object value = bindings.get(name);
      if (value != null) {
        return value;
      }
    }

    return null;
  }
}
