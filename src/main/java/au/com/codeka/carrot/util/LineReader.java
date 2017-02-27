package au.com.codeka.carrot.util;

import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.resource.ResourcePointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A helper class that reads a {@link Reader} one line at a time, keeping track of the current line and colume via
 * a {@link ResourcePointer}.
 */
public class LineReader {
  private final BufferedReader reader;

  private ResourcePointer ptr;
  private char[] line;
  private int lineIndex;

  public LineReader(ResourceName resourceName, Reader reader) {
    this(new ResourcePointer(resourceName), reader);
  }

  public LineReader(ResourcePointer ptr, Reader reader) {
    this.ptr = ptr;
    this.reader = new BufferedReader(reader);
    line = null;
    lineIndex = ptr.getLineNo();
  }

  public int nextChar() throws IOException {
    if (line == null) {
      String str = reader.readLine();
      if (str == null) {
        return -1;
      }
      line = str.toCharArray();
      lineIndex = 0;
      ptr = ptr.nextLine(str);
    }
    int ch;
    if (lineIndex >= line.length) {
      String str = reader.readLine();
      if (str == null) {
        return -1;
      } else {
        ch = '\n';
        line = str.toCharArray();
        lineIndex = 0;
        ptr = ptr.nextLine(str);
      }
    } else {
      ch = line[lineIndex++];
    }
    ptr = ptr.nextCol();
    return ch;
  }

  public ResourcePointer getPointer() {
    return ptr;
  }
}
