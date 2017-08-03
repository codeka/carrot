package au.com.codeka.carrot;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The Bindings of a template when rendered.
 *
 * @author marten
 */
public interface Bindings {

    /**
     * Returns a value for the given key or {@code null} if no such key exists in these Bindings.
     *
     * @param key The key to resolve.
     * @return An {@link Object} or {@code null}.
     */
    @Nullable
    Object resolve(@Nonnull String key);

    /**
     * Returns whether these Bindings contain any values.
     *
     * @return {@code true} if these Bindings are empty, {@code false} if there is at least one value.
     */
    boolean isEmpty();
}
