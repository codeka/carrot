package au.com.codeka.carrot.util;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.bindings.MapBindings;
import au.com.codeka.carrot.resource.MemoryResourceLocator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Helpers for rendering templates in tests.
 */
public class RenderHelper {
  public static String render(String content, Object... bindings) throws CarrotException {
    CarrotEngine engine = new CarrotEngine(new Configuration.Builder()
        .setLogger(new Configuration.Logger() {
          @Override
          public void print(int level, String msg) {
            if (level > Configuration.Logger.LEVEL_DEBUG) {
              System.err.println(msg);
            }
          }
        })
        .setResourceLocator(new MemoryResourceLocator.Builder().add("index", content))
        .build());

    Map<String, Object> bindingsMap = new HashMap<>();
    for (int i = 0; i < bindings.length; i += 2) {
      bindingsMap.put(bindings[i].toString(), bindings[i + 1]);
    }

    return engine.process("index", new MapBindings(bindingsMap));
  }
}
