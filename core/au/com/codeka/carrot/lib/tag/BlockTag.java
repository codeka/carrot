package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.HelperStringTokenizer;
import au.com.codeka.carrot.util.ListOrderedMap;

/**
 * {% block name %}
 * 
 * @author anysome TODO EXTENDS NESTED
 */

public class BlockTag implements Tag {

  final String BLOCKNAMES = "'BLK\"NAMES";
  final String TAGNAME = "block";
  final String ENDTAGNAME = "endblock";

  @SuppressWarnings("unchecked")
  @Override
  public void interpreter(NodeList carries, String helpers, JangodInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new InterpretException("Tag 'block' expects 1 helper >>> " + helper.length);
    }
    String blockName = interpreter.resolveString(helper[0]);
    // check block name is unique
    List<String> blockNames = (List<String>) interpreter.fetchRuntimeScope(BLOCKNAMES, 1);
    if (blockNames == null) {
      blockNames = new ArrayList<String>();
    }
    if (blockNames.contains(blockName)) {
      throw new InterpretException("Can't redefine the block with name >>> " + blockName);
    } else {
      blockNames.add(blockName);
      interpreter.assignRuntimeScope(BLOCKNAMES, blockNames, 1);
    }
    Object isChild = interpreter.fetchRuntimeScope(JangodInterpreter.CHILD_FLAG, 1);
    if (isChild != null) {
      ListOrderedMap blockList = (ListOrderedMap) interpreter.fetchRuntimeScope(
          JangodInterpreter.BLOCK_LIST, 1);
      // check block was defined in parent
      if (!blockList.containsKey(blockName)) {
        throw new InterpretException("Dosen't define block in extends parent with name >>> "
            + blockName);
      }
      // cover parent block content with child's.
      blockList.put(blockName, getBlockContent(carries, interpreter));
      return;
    }
    Object isParent = interpreter.fetchRuntimeScope(JangodInterpreter.PARENT_FLAG, 1);
    if (isParent != null) {
      // save block content to engine, and return identify
      ListOrderedMap blockList = (ListOrderedMap) interpreter.fetchRuntimeScope(
          JangodInterpreter.BLOCK_LIST, 1);
      blockList.put(blockName, getBlockContent(carries, interpreter));
      writer.write(JangodInterpreter.SEMI_BLOCK + blockName);
      return;
    }
    writeBlockContent(carries, interpreter, writer);
  }

  private void writeBlockContent(NodeList carries, JangodInterpreter interpreter, Writer writer)
      throws CarrotException, IOException {
    for (Node node : carries) {
      node.render(interpreter, writer);
    }
  }

  private String getBlockContent(NodeList carrier, JangodInterpreter interpreter)
      throws CarrotException, IOException {
    StringWriter writer = new StringWriter();
    writeBlockContent(carrier, interpreter, writer);
    return writer.toString();
  }

  @Override
  public String getEndTagName() {
    return ENDTAGNAME;
  }

  @Override
  public String getName() {
    return TAGNAME;
  }

}
