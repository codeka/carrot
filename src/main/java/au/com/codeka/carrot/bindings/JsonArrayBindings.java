package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.ValueHelper;
import org.json.JSONArray;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * {@link Bindings} based on the values in a {@link JSONArray}.
 *
 * @author Marten Gajda
 */
public final class JsonArrayBindings implements Bindings, Iterable<Object> {
  private final JSONArray jsonArray;

  public JsonArrayBindings(JSONArray jsonArray) {
    this.jsonArray = jsonArray;
  }

  @Override
  public Object resolve(@Nonnull String key) {
    return ValueHelper.jsonHelper(jsonArray.get(Integer.parseInt(key)));
  }

  @Override
  public boolean isEmpty() {
    return jsonArray.length() == 0;
  }

  @Override
  public Iterator<Object> iterator() {
    return new Iterator<Object>() {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < jsonArray.length();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("Remove not supported.");
      }

      @Override
      public Object next() {
        if (!hasNext()) {
          throw new NoSuchElementException("No further elements to iterate");
        }
        return ValueHelper.jsonHelper(jsonArray.get(index++));
      }
    };
  }
}
