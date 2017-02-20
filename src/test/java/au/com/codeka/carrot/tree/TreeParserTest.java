package au.com.codeka.carrot.tree;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.parse.Tokenizer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link TreeParser}.
 */
@RunWith(JUnit4.class)
public class TreeParserTest {
  @Test
  public void testEmptyTree() {
    Node node = parseTree("");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).isEmpty();
  }

  @Test
  public void testSingleFixedToken() {
    Node node = parseTree("Hello World");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(1);
    assertThat(node.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(0)).getContent()).isEqualTo("Hello World");
  }

  @Test
  public void testFixedCommentFixed() {
    Node node = parseTree("Hello{# foo #}World");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(2);
    assertThat(node.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(0)).getContent()).isEqualTo("Hello");
    assertThat(node.getChildren().get(1)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(1)).getContent()).isEqualTo("World");
  }

  @Test
  public void testFixedEchoFixed() {
    Node node = parseTree("Hello{{ foo }}World");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(3);
    assertThat(node.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(0)).getContent()).isEqualTo("Hello");
    assertThat(node.getChildren().get(1)).isInstanceOf(TagNode.class);
    assertThat(((TagNode) node.getChildren().get(1)).getTag().getTagName()).isEqualTo("echo");
    assertThat(node.getChildren().get(2)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(2)).getContent()).isEqualTo("World");
  }

  @Test
  public void testIfFixedEnd() {
    Node node = parseTree("{% if foo %}Hello World{% end %}");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(1);
    assertThat(node.getChildren().get(0)).isInstanceOf(TagNode.class);

    TagNode ifNode = (TagNode) node.getChildren().get(0);
    assertThat(ifNode.getTag().getTagName()).isEqualTo("if");

    assertThat(ifNode.getChildren()).isNotNull();
    assertThat(ifNode.getChildren()).hasSize(1);
    assertThat(ifNode.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) ifNode.getChildren().get(0)).getContent()).isEqualTo("Hello World");
  }

  private Node parseTree(String input) {
    TreeParser treeParser = new TreeParser(new Configuration());
    try {
      Node node = treeParser.parse(new Tokenizer(new StringReader(input)));
      assertThat(node).isNotNull();
      return node;
    } catch (CarrotException e) {
      throw new RuntimeException(e);
    }
  }
}
