package au.com.codeka.carrot.resource;

import javax.annotation.Nullable;

/**
 * Represents a "pointer" into a resource, useful for debugging and so on (for example, for displaying the line number
 * and column where there's an error).
 */
public class ResourcePointer {
  @Nullable private final ResourceName resourceName;
  private final String line;
  private final int lineNo;
  private final int col;

  public ResourcePointer(ResourceName resourceName) {
    this.resourceName = resourceName;
    this.line = null;
    this.lineNo = 0;
    this.col = 0;
  }

  public ResourcePointer(ResourceName resourceName, String line, int lineNo, int col) {
    this.resourceName = resourceName;
    this.line = line;
    this.lineNo = lineNo;
    this.col = col;
  }

  public ResourcePointer nextCol() {
    return new ResourcePointer(resourceName, line, lineNo, col + 1);
  }

  public ResourcePointer nextLine(String line) {
    return new ResourcePointer(resourceName, line, lineNo + 1, 1);
  }

  public int getLineNo() {
    return lineNo;
  }

  @Override
  public String toString() {
    String str = (resourceName == null ? "???" : resourceName.getName()) + "\n";
    String prefix = Integer.toString(lineNo) + ": ";
    str += prefix + line;
    str += "\n";
    str += new String(new char[prefix.length() + col - 1]).replace('\0', ' ');
    str += "^";
    return str;
  }
}
