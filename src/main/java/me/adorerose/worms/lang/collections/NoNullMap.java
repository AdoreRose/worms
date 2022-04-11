package me.adorerose.worms.lang.collections;

import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class NoNullMap<K, V> extends HashMap<K, V> {

    @Override
    public V put(@Nonnull K key, @Nonnull V value) {
        Validate.notNull(key, "key");
        Validate.notNull(value, "value");
        return super.put(key, value);
    }

    public static <K, V> NoNullMap<K, V> newInstance() {
        return new NoNullMap<>();
    }
}
