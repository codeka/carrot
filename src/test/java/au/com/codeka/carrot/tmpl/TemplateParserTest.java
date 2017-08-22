package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.resource.ResourcePointer;
import au.com.codeka.carrot.tag.EchoTag;
import au.com.codeka.carrot.tag.IfTag;
import au.com.codeka.carrot.tmpl.parse.Tokenizer;
import au.com.codeka.carrot.util.LineReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link TemplateParser}.
 */
@RunWith(JUnit4.class)
public class TemplateParserTest {
  @Test
  public void testEmptyTree() {
    Node node = parseTemplate("");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).isEmpty();
  }

  @Test
  public void testSingleFixedToken() {
    Node node = parseTemplate("Hello World");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(1);
    assertThat(node.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(0)).getContent()).isEqualTo("Hello World");
  }

  @Test
  public void testFixedCommentFixed() {
    Node node = parseTemplate("Hello{# foo #}World");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(2);
    assertThat(node.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(0)).getContent()).isEqualTo("Hello");
    assertThat(node.getChildren().get(1)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(1)).getContent()).isEqualTo("World");
  }

  @Test
  public void testFixedEchoFixed() {
    Node node = parseTemplate("Hello{{ foo }}World");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(3);
    assertThat(node.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(0)).getContent()).isEqualTo("Hello");
    assertThat(node.getChildren().get(1)).isInstanceOf(TagNode.class);
    assertThat(((TagNode) node.getChildren().get(1)).getTag()).isInstanceOf(EchoTag.class);
    assertThat(node.getChildren().get(2)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) node.getChildren().get(2)).getContent()).isEqualTo("World");
  }

  @Test
  public void testIfFixedEnd() {
    Node node = parseTemplate("{% if foo %}Hello World{% end %}");
    assertThat(node.getChildren()).isNotNull();
    assertThat(node.getChildren()).hasSize(1);
    assertThat(node.getChildren().get(0)).isInstanceOf(TagNode.class);

    TagNode ifNode = (TagNode) node.getChildren().get(0);
    assertThat(ifNode.getTag()).isInstanceOf(IfTag.class);

    assertThat(ifNode.getChildren()).isNotNull();
    assertThat(ifNode.getChildren()).hasSize(1);
    assertThat(ifNode.getChildren().get(0)).isInstanceOf(FixedNode.class);
    assertThat(((FixedNode) ifNode.getChildren().get(0)).getContent()).isEqualTo("Hello World");
  }

  private Node parseTemplate(String input) {
    TemplateParser templateParser = new TemplateParser(new Configuration.Builder().build());
    try {
      Node node =
          templateParser.parse(new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(input))));
      assertThat(node).isNotNull();
      return node;
    } catch (CarrotException e) {
      throw new RuntimeException(e);
    }
  }
}
