package edu.course.lab04;

import java.util.HashMap;
import java.util.Map;

/**
 * Реестр наблюдений с ключом {@link SampleId}.
 * Внутри используется {@link HashMap}; доступ к карте снаружи не предоставляется.
 */
public final class SampleRegistry {

    private final Map<SampleId, DataSample> samples = new HashMap<>();

    /**
     * Добавляет наблюдение или заменяет существующее с логически равным ключом.
     */
    public void put(SampleId id, DataSample sample) {
        requireKey(id);
        if (sample == null) {
            throw new IllegalArgumentException("Наблюдение не может быть null");
        }
        samples.put(id, sample);
    }

    public DataSample get(SampleId id) {
        requireKey(id);
        return samples.get(id);
    }

    public boolean remove(SampleId id) {
        requireKey(id);
        return samples.remove(id) != null;
    }

    public boolean contains(SampleId id) {
        requireKey(id);
        return samples.containsKey(id);
    }

    public int size() {
        return samples.size();
    }

    private static void requireKey(SampleId id) {
        if (id == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }
    }
}
