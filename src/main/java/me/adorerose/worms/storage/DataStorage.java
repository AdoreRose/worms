package me.adorerose.worms.storage;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public abstract class DataStorage<T> {
    protected Map<String, LinkedDataAccessor> linkedAccessors;
    protected T handle;

    protected DataStorage(T handle) {
        this.handle = handle;
    }

    protected DataStorage() { }

    public void registerLinkedAccessor(String section, LinkedDataAccessor accessor) {
        if (linkedAccessors == null) linkedAccessors = Maps.newHashMap();
        Validate.isTrue(!linkedAccessors.containsKey(section));
        linkedAccessors.put(section, accessor);
    }

    public static <T> T getStorage(Class<T> storageClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        return storageClass.getConstructor().newInstance();
    }

    public enum SavingTarget {
        STORAGE(0x1),
        LINKED_ACCESSORS(0x2),
        ALL(0x3);

        SavingTarget(int value) {
            this.value = value;
        }

        public boolean contains(SavingTarget target) {
            return (this.value & target.value) == target.value;
        }

        public int value;
    }
}