package es.unex.giiis.bss.jgarciapft.model;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Memoization component for singular values, not designed for functions. An initial value can be supplied. An array of
 * dependencies may be supplied to indicate when to update the memoized value.
 * <p>
 * IMPORTANT: Dependencies CANNOT BE of a PRIMITIVE type !!! Use wrappers instead
 * <p>
 * Supports up to 2-dimensional arrays
 *
 * @param <T> Type of the memoized value
 */
public class MemoizedValue<T> {

    private T memoValue;
    private final Supplier<T> updateCallback;
    private final Object[] dependencies;

    private Object[] dependenciesStore;

    public MemoizedValue(Supplier<T> updateCallback, Object[] dependencies) {
        this.updateCallback = updateCallback;
        this.dependencies = dependencies;

        for (Object dependency : dependencies) {
            if (!(dependency instanceof Serializable))
                throw new IllegalArgumentException(dependency + " isn't serializable");

            if (dependency.getClass().isPrimitive())
                throw new IllegalArgumentException(dependency + " can't be of a primitive type");

            if (dependency.getClass().isArray()) {
                Object[] firstLevelArray = (Object[]) dependency;
                if (!firstLevelArray[0].getClass().isArray()) {
                    if (firstLevelArray.getClass().getComponentType().isPrimitive())
                        throw new IllegalArgumentException("Dependency can't be an array of a primitive underlying type");
                } else {
                    if (firstLevelArray[0].getClass().getComponentType().isPrimitive()) {
                        throw new IllegalArgumentException("Dependency can't be a matrix of a primitive underlying type");
                    }
                }
            }
        }

        refreshDependenciesStore();
    }

    public MemoizedValue(T initialValue, Supplier<T> updateCallback, Object[] dependencies) {
        memoValue = initialValue;
        this.updateCallback = updateCallback;
        this.dependencies = dependencies;

        for (Object dependency : dependencies) {
            if (!(dependency instanceof Serializable))
                throw new IllegalArgumentException(dependency + " isn't serializable");

            if (dependency.getClass().isPrimitive() ||
                    (dependency.getClass().isArray() && dependency.getClass().getComponentType().getComponentType().isPrimitive()))
                throw new IllegalArgumentException(dependency + " can't of primitive type");
        }

        refreshDependenciesStore();
    }

    public T get() {
        if (isDirty()) {
            memoValue = updateCallback.get();
            refreshDependenciesStore();
        }

        return memoValue;
    }

    private void refreshDependenciesStore() {
        // Deep copy of dependencies
        dependenciesStore = SerializationUtils.clone(dependencies);
    }

    private boolean isDirty() {
        for (int i = 0; i < dependencies.length; i++) {
            Object currentDependency = dependencies[i];
            if (!currentDependency.getClass().isArray()) {
                if (!currentDependency.equals(dependenciesStore[i])) return true;
            } else {
                Object[] firstLevelArray = (Object[]) currentDependency;
                if (!firstLevelArray[0].getClass().isArray()) {
                    if (!Arrays.equals(firstLevelArray, (Object[]) dependenciesStore[i])) return true;
                } else {
                    for (int j = 0; j < firstLevelArray.length; j++) {
                        Object[] secondLevelArray = (Object[]) firstLevelArray[j];
                        if (!Arrays.equals(secondLevelArray, (Object[]) ((Object[]) dependenciesStore[i])[j]))
                            return true;
                    }
                }
            }
        }

        return false;
    }

}
