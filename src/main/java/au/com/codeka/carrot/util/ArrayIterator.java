package au.com.codeka.carrot.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator<Object> {

  private Object array;
  private int length;
  private int cursor;

  public ArrayIterator(Object obj) {
    array = obj;
    length = Array.getLength(obj);
    cursor = 0;
  }

  @Override
  public boolean hasNext() {
    return cursor < length;
  }

  @Override
  public Object next() {
    if (cursor >= length) {
      throw new NoSuchElementException();
    }
    return Array.get(array, cursor++);
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
