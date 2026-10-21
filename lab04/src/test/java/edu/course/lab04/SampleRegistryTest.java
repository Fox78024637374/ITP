package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SampleRegistryTest {

    private static DataSample sample(String label) {
        return new DataSample(label, new double[]{1.0, 2.0, 3.0});
    }

    @Test
    void putAndGetValue() {
        SampleRegistry registry = new SampleRegistry();
        DataSample sample = sample("first");

        registry.put(new SampleId("iris", 1), sample);

        assertSame(sample, registry.get(new SampleId("iris", 1)));
    }

    @Test
    void twoEqualKeyInstancesFindSameValue() {
        SampleRegistry registry = new SampleRegistry();
        DataSample sample = sample("first");
        SampleId storedKey = new SampleId("iris", 1);
        registry.put(storedKey, sample);

        SampleId lookupKey = new SampleId("iris", 1);

        assertNotSame(storedKey, lookupKey);
        assertEquals(storedKey, lookupKey);
        assertSame(sample, registry.get(lookupKey));
    }

    @Test
    void puttingEqualKeyReplacesValueWithoutGrowingRegistry() {
        SampleRegistry registry = new SampleRegistry();
        DataSample first = sample("first");
        DataSample second = sample("second");

        registry.put(new SampleId("iris", 1), first);
        registry.put(new SampleId("iris", 1), second);

        assertEquals(1, registry.size());
        assertSame(second, registry.get(new SampleId("iris", 1)));
    }

    @Test
    void removeDeletesValue() {
        SampleRegistry registry = new SampleRegistry();
        registry.put(new SampleId("iris", 1), sample("first"));

        assertTrue(registry.remove(new SampleId("iris", 1)));
        assertFalse(registry.remove(new SampleId("iris", 1)));
        assertNull(registry.get(new SampleId("iris", 1)));
        assertEquals(0, registry.size());
    }

    @Test
    void containsReflectsPresenceOfKey() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id = new SampleId("iris", 1);

        assertFalse(registry.contains(id));

        registry.put(id, sample("first"));
        assertTrue(registry.contains(new SampleId("iris", 1)));

        registry.remove(id);
        assertFalse(registry.contains(id));
    }

    @Test
    void sizeCountsDistinctKeys() {
        SampleRegistry registry = new SampleRegistry();

        registry.put(new SampleId("iris", 1), sample("first"));
        registry.put(new SampleId("iris", 2), sample("second"));
        registry.put(new SampleId("wine", 1), sample("third"));

        assertEquals(3, registry.size());
    }

    @Test
    void rejectsNullArguments() {
        SampleRegistry registry = new SampleRegistry();

        assertThrows(IllegalArgumentException.class, () -> registry.put(null, sample("first")));
        assertThrows(IllegalArgumentException.class, () -> registry.put(new SampleId("iris", 1), null));
        assertThrows(IllegalArgumentException.class, () -> registry.get(null));
        assertThrows(IllegalArgumentException.class, () -> registry.remove(null));
        assertThrows(IllegalArgumentException.class, () -> registry.contains(null));
    }

    @Test
    void unequalKeysWithSameHashCodeKeepDifferentValues() {
        Map<CollidingKey, String> map = new HashMap<>();
        CollidingKey first = new CollidingKey("first");
        CollidingKey second = new CollidingKey("second");

        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, second);

        map.put(first, "value-1");
        map.put(second, "value-2");

        assertEquals(2, map.size());
        assertEquals("value-1", map.get(new CollidingKey("first")));
        assertEquals("value-2", map.get(new CollidingKey("second")));
    }

    @Test
    void changingMutableKeyBreaksLookup() {
        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey(1);
        map.put(key, "value");

        assertEquals("value", map.get(key));

        key.setId(2);

        assertNull(map.get(key));
        assertFalse(map.containsKey(key));
        // Запись осталась в старой корзине, но найти ее по измененному ключу уже нельзя.
        assertEquals(1, map.size());
    }

    private static final class CollidingKey {
        private final String value;

        private CollidingKey(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CollidingKey key)) {
                return false;
            }
            return value.equals(key.value);
        }

        @Override
        public int hashCode() {
            // Намеренно одинаковый hashCode у всех ключей: проверяем коллизию.
            return 42;
        }
    }

    /**
     * Учебный изменяемый ключ. Используется только в тесте, чтобы показать
     * проблему изменения поля, участвующего в hashCode.
     */
    private static final class MutableKey {
        private int id;

        private MutableKey(int id) {
            this.id = id;
        }

        private void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MutableKey key)) {
                return false;
            }
            return id == key.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
