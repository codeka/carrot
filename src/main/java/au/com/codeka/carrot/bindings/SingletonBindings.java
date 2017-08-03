package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;


/**
 * {@link Bindings} with exactly one value.
 *
 * @author Marten Gajda
 */
public final class SingletonBindings implements Bindings {
    private final String key;
    private final Object value;


    public SingletonBindings(@Nonnull String key, Object value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public Object resolve(@Nonnull String key) {
        return this.key.equals(key) ? value : null;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }
}
