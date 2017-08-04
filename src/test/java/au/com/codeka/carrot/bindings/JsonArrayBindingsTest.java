package au.com.codeka.carrot.bindings;

import org.hamcrest.CoreMatchers;
import org.json.JSONArray;
import org.junit.Test;

import static org.junit.Assert.assertThat;


/**
 * @author marten
 */
public class JsonArrayBindingsTest {
    private final static String JSON_ARRAY = "[\n" +
            "  \"value\",\n" +
            "  2,\n" +
            "  true,\n" +
            "  [\n" +
            "    \"a\",\n" +
            "    \"b\"\n" +
            "  ],\n" +
            "  {\n" +
            "    \"inner\": \"value\"\n" +
            "  },\n" +
            "  null\n" +
            "]";


    @Test
    public void testResolved() throws Exception {
        assertThat(new JsonArrayBindings(new JSONArray(JSON_ARRAY)).resolve("0"), CoreMatchers.<Object>is("value"));
        assertThat(new JsonArrayBindings(new JSONArray(JSON_ARRAY)).resolve("1"), CoreMatchers.<Object>is(2));
        assertThat(new JsonArrayBindings(new JSONArray(JSON_ARRAY)).resolve("2"), CoreMatchers.<Object>is(true));
        assertThat(new JsonArrayBindings(new JSONArray(JSON_ARRAY)).resolve("3"), CoreMatchers.instanceOf(JsonArrayBindings.class));
        assertThat(new JsonArrayBindings(new JSONArray(JSON_ARRAY)).resolve("4"), CoreMatchers.instanceOf(JsonObjectBindings.class));
        assertThat(new JsonArrayBindings(new JSONArray(JSON_ARRAY)).resolve("5"), CoreMatchers.nullValue());
    }


    @Test
    public void testIsEmpty() throws Exception {
        assertThat(new JsonArrayBindings(new JSONArray()).isEmpty(), CoreMatchers.is(true));
        assertThat(new JsonArrayBindings(new JSONArray(JSON_ARRAY)).isEmpty(), CoreMatchers.is(false));
    }

}