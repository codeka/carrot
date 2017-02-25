package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

import java.io.IOException;
import java.io.Writer;

/**
 * Special node that represents the root of the syntax tree.
 */
public class RootNode extends Node {
  public RootNode() {
    super(true /* isBlockNode */);
  }

  @Override
  public void render(CarrotEngine engine, Writer writer, Scope scope) throws CarrotException, IOException {
    renderChildren(engine, writer, scope);
  }
}
