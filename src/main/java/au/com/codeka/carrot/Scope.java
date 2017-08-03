package au.com.codeka.carrot;

import au.com.codeka.carrot.bindings.Composite;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static au.com.codeka.carrot.util.Preconditions.checkNotNull;

/**
 * Scope is a collection of all the bindings that are active. The scope is basically a stack: each time you iterate
 * down a node, you can push new bindings onto the stack and those will be the first one searched for a variable.
 */
public class Scope {
  private final Deque<Bindings> stack = new ArrayDeque<>();

  /**
   * Create a new {@link Scope}, with the given initial set of "global" bindings.
   *
   * @param globalBindings A set of "global" bindings that you want first on the stack.
   */
  public Scope(Bindings globalBindings) {
    stack.add(globalBindings);
  }

  /**
   * Push the given bindings onto the stack.
   *
   * @param bindings The bindings to push.
   */
  public void push(Bindings bindings) {
    stack.push(bindings);
  }

  /** Pop the most recent bindings off the stack. */
  public void pop() {
    stack.pop();
  }


  /**
   * Extend the current bindings with the given one.
   *
   * @param bindings The new {@link Bindings}.
   */
  public void extendCurrent(Bindings bindings) {
    stack.push(new Composite(bindings, stack.pop()));
  }
  /**
   * Resolve the given named variable from the stack of bindings, most recently-pushed to last.
   *
   * @param name The name to resolve.
   * @return The resolved value, or null if the value doesn't exist in this scope.
   */
  @Nullable
  public Object resolve(@Nonnull String name) {
    checkNotNull(name);

    for (Bindings bindings : stack) {
      Object value = bindings.resolve(name);
      if (value != null) {
        return value;
      }
    }

    return null;
  }
}
