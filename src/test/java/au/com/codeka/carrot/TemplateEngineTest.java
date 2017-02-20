package au.com.codeka.carrot;

import au.com.codeka.carrot.resource.ResourceLocater;
import au.com.codeka.carrot.resource.ResourceName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.Nullable;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * These are basically end-to-end tests of the entire shebang.
 */
@RunWith(JUnit4.class)
public class TemplateEngineTest {
  @Test
  public void testEmptyTemplate() {
    assertThat(render("", null)).isEqualTo("");
  }

  @Test
  public void testSingleFixed() {
    assertThat(render("Hello World", null)).isEqualTo("Hello World");
  }

  private String render(String template, @Nullable Map<String, Object> bindings) {
    TemplateEngine engine = new TemplateEngine();
    return render(engine, template, bindings);
  }

  private String render(TemplateEngine engine, String template, @Nullable Map<String, Object> bindings) {
    engine.getConfig().setResourceLocater(new TestResourceLocator(template));
    try {
      return engine.process("", bindings);
    } catch (CarrotException e) {
      throw new RuntimeException(e);
    }
  }

  /** A test {@link ResourceLocater} that simple returns a static string content. */
  private class TestResourceLocator implements ResourceLocater {
    private String content;

    public TestResourceLocator(String content) {
      this.content = content;
    }

    @Override
    public ResourceName findResource(@Nullable ResourceName parent, String name) throws CarrotException {
      return new ResourceName(parent, name) {
        @Nullable
        @Override
        public ResourceName getParent() {
          return null;
        }
      };
    }

    @Override
    public ResourceName findResource(String name) throws CarrotException {
      return findResource(null, name);
    }

    @Override
    public long getModifiedTime(ResourceName resourceName) throws CarrotException {
      return 0;
    }

    @Override
    public Reader getReader(ResourceName resourceName) throws CarrotException {
      return new StringReader(content);
    }
  }
}
