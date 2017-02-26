package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotEngineTest;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.resource.MemoryResourceLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.TreeMap;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for {@link ExtendsTag} (and, by extension, {@link BlockTag}).
 */
@RunWith(JUnit4.class)
public class ExtendsTagTest {
  @Test
  public void testBasic() {
    CarrotEngine engine = createEngine(
        "skeleton", "Hello{% block \"foo\" %}blah blah{% end %}World",
        "index", "{% extends \"skeleton\" %}{% block \"foo\" %}yada yada{% end %}"
      );
    String result = render(engine, "index", new TreeMap<>());
    assertThat(result).isEqualTo("Helloyada yadaWorld");
  }

  @Test
  public void testMultipleBlocks() {
    CarrotEngine engine = createEngine(
        "skeleton", "Hello{% block \"foo\" %}blah blah{% endblock %}World{% block \"bar\" %}{% endblock %}",
        "index", "{% extends \"skeleton\" %}{% block \"foo\" %}yada yada{% end %}{% block \"bar\" %}stuff{% endblock %}"
    );
    String result = render(engine, "index", new TreeMap<>());
    assertThat(result).isEqualTo("Helloyada yadaWorldstuff");
  }

  @Test
  public void testMissingBlock() {
    CarrotEngine engine = createEngine(
        "skeleton", "Hello{% block \"foo\" %}blah blah{% end %}World",
        "index", "{% extends \"skeleton\" %}"
    );
    String result = render(engine, "index", new TreeMap<>());
    assertThat(result).isEqualTo("Helloblah blahWorld");
  }

  @Test
  public void testSkeletonNotFound() {
    CarrotEngine engine = createEngine(
        "index", "{% extends \"skeleton\" %}"
    );
    try {
      engine.process("index", new TreeMap<>());
      fail("Expected CarrotException.");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("File not found: skeleton");
    }
  }

  private String render(CarrotEngine engine, String templateName, @Nullable Map<String, Object> bindings) {
    try {
      return engine.process(templateName, bindings);
    } catch (CarrotException e) {
      throw new RuntimeException(e);
    }
  }

  private CarrotEngine createEngine(String... nameValues) {
    CarrotEngine engine = new CarrotEngine();
    engine.getConfig().setLogger((level, msg) -> System.err.println(msg));
    engine.getConfig().setResourceLocater(createResources(nameValues));
    return engine;
  }

  private MemoryResourceLocator createResources(String... nameValues) {
    Map<String, String> resources = new TreeMap<>();
    for (int i = 0; i < nameValues.length; i += 2) {
      resources.put(nameValues[i], nameValues[i+1]);
    }
    return new MemoryResourceLocator(resources);
  }
}
