package au.com.codeka.carrot.util;

import java.util.Iterator;

/** An 'enhanced' iterator which keeps track of various state about the loop. */
public class ForLoop<T> implements Iterator<T> {

  private int index = -1;
  private int counter = 0;
  private int revindex = -9; // if don't know length
  private int revcounter = -9; // if don't know length
  private int length = -9; // if don't know length
  private boolean first = true;
  private boolean last;

  private Iterator<T> it;

  public ForLoop(Iterator<T> ite, int len) {
    length = len;
    if (len < 2) {
      revindex = 1;
      revcounter = 2;
      last = true;
    } else {
      revindex = len;
      revcounter = len + 1;
      last = false;
    }
    it = ite;
  }

  public ForLoop(Iterator<T> ite) {
    it = ite;
    if (it.hasNext()) {
      last = false;
    } else {
      length = 0;
      revindex = 1;
      revcounter = 2;
      last = true;
    }
  }

  @Override
  public T next() {
    T res;
    if (it.hasNext()) {
      index++;
      counter++;
      if (length != -9) {
        revindex--;
        revcounter--;
      }
      res = it.next();
      if (!it.hasNext()) {
        last = true;
        length = counter;
        revindex = 0;
        revcounter = 1;
      }
      if (index > 0) {
        first = false;
      }
    } else {
      res = null;
    }
    return res;
  }

  public int getIndex() {
    return index;
  }

  public int getCounter() {
    return counter;
  }

  public int getRevindex() {
    if (revindex == -9) {
      // TODO: log?
    }
    return revindex;
  }

  public int getRevcounter() {
    if (revcounter == -9) {
      // TODO: log?
    }
    return revcounter;
  }

  public int getLength() {
    if (revcounter == -9) {
      // TODO: log?
    }
    return length;
  }

  public boolean isFirst() {
    return first;
  }

  public boolean isLast() {
    return last;
  }

  @Override
  public boolean hasNext() {
    return it.hasNext();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
