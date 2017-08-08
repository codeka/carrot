package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.ValueHelper;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * {@link Bindings} based on the values in a {@link JSONObject}.
 *
 * @author Marten Gajda
 */
public final class JsonObjectBindings implements Bindings, Iterable<EntryBindings> {
  private final JSONObject jsonObject;

  public JsonObjectBindings(JSONObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  @Override
  public Object resolve(@Nonnull String key) {
    return ValueHelper.jsonHelper(jsonObject.opt(key));
  }

  @Override
  public boolean isEmpty() {
    return jsonObject.length() == 0;
  }

  @Override
  public Iterator<EntryBindings> iterator() {
    final Iterator<String> keys = jsonObject.keys();

    // return an iterator of Map Entries which allows iterating json objects like this:
    // {% for item in json %}
    //    {{ item.key }} -> {{ item.value }}
    // {% end %}
    return new Iterator<EntryBindings>() {
      @Override
      public boolean hasNext() {
        return keys.hasNext();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("Remove not supported.");
      }

      @Override
      public EntryBindings next() {
        final String key = keys.next();
        return new EntryBindings(key, ValueHelper.jsonHelper(jsonObject.opt(key)));
      }
    };
  }
}
