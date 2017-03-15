package au.com.codeka.carrot.util;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.resource.MemoryResourceLocator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Helpers for rendering templates in tests.
 */
public class RenderHelper {
  public static String render(String content, Object... bindings) throws CarrotException {
    CarrotEngine engine = new CarrotEngine();
    engine.getConfig().setLogger((level, msg) -> {
      if (level > Configuration.Logger.LEVEL_DEBUG) {
        System.err.println(msg);
      }
    });

    Map<String, String> resources = new TreeMap<>();
    resources.put("index", content);
    MemoryResourceLocator resourceLocator = new MemoryResourceLocator(resources);
    engine.getConfig().setResourceLocater(resourceLocator);

    Map<String, Object> bindingsMap = new HashMap<>();
    for (int i = 0; i < bindings.length; i += 2) {
      bindingsMap.put(bindings[i].toString(), bindings[i + 1]);
    }

    return engine.process("index", bindingsMap);
  }
}
