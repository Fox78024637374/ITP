package edu.course.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DataSampleTest {

    private DataSample sample() {
        return new DataSample(
            new SampleId("id-1"), "cat", SampleStatus.NEW, new double[]{1.0, 2.0, 3.0}
        );
    }

    // ---------- конструктор ----------

    @Test
    void constructorCreatesValidSample() {
        DataSample s = sample();
        assertEquals(new SampleId("id-1"), s.getId());
        assertEquals("cat", s.getLabel());
        assertEquals(SampleStatus.NEW, s.getStatus());
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, s.getFeatures());
    }

    @Test
    void constructorRejectsNullId() {
        assertThrows(IllegalArgumentException.class,
            () -> new DataSample(null, "cat", SampleStatus.NEW, new double[]{1.0}));
    }

    @Test
    void constructorRejectsNullOrBlankLabel() {
        assertThrows(IllegalArgumentException.class,
            () -> new DataSample(new SampleId("id-1"), null,
                                 SampleStatus.NEW, new double[]{1.0}));
        assertThrows(IllegalArgumentException.class,
            () -> new DataSample(new SampleId("id-1"), " ",
                                 SampleStatus.NEW, new double[]{1.0}));
    }

    @Test
    void constructorRejectsNullStatus() {
        assertThrows(IllegalArgumentException.class,
            () -> new DataSample(new SampleId("id-1"), "cat",
                                 null, new double[]{1.0}));
    }

    @Test
    void constructorRejectsNullOrEmptyFeatures() {
        assertThrows(IllegalArgumentException.class,
            () -> new DataSample(new SampleId("id-1"), "cat",
                                 SampleStatus.NEW, null));
        assertThrows(IllegalArgumentException.class,
            () -> new DataSample(new SampleId("id-1"), "cat",
                                 SampleStatus.NEW, new double[]{}));
    }

    // ---------- SampleId ----------

    @Test
    void sampleIdRejectsNullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId(null));
        assertThrows(IllegalArgumentException.class, () -> new SampleId(""));
        assertThrows(IllegalArgumentException.class, () -> new SampleId("   "));
    }

    @Test
    void sampleIdTrimsValue() {
        assertEquals(new SampleId("id-1"), new SampleId("  id-1  "));
    }

    @Test
    void sampleIdEqualsAndHashCodeWork() {
        SampleId a = new SampleId("id-1");
        SampleId b = new SampleId("id-1");
        SampleId c = new SampleId("id-2");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertFalse(a.equals(c));
    }

    // ---------- changeStatus / isReady ----------

    @Test
    void changeStatusUpdatesStatus() {
        DataSample s = sample();
        s.changeStatus(SampleStatus.IN_PROGRESS);
        assertEquals(SampleStatus.IN_PROGRESS, s.getStatus());
    }

    @Test
    void changeStatusRejectsNull() {
        DataSample s = sample();
        assertThrows(IllegalArgumentException.class, () -> s.changeStatus(null));
    }

    @Test
    void isReadyOnlyTrueForReadyStatus() {
        DataSample s = sample();
        assertFalse(s.isReady());

        s.changeStatus(SampleStatus.READY);
        assertTrue(s.isReady());

        s.changeStatus(SampleStatus.REJECTED);
        assertFalse(s.isReady());
    }

    // ---------- meanFeatures ----------

    @Test
    void meanFeaturesCalculated() {
        DataSample s = sample(); // {1,2,3}
        assertEquals(2.0, s.meanFeatures(), 1e-9);
    }

    // ---------- defensive copy ----------

    @Test
    void featuresAreCopiedInConstructor() {
        double[] original = {1.0, 2.0, 3.0};
        DataSample s = new DataSample(new SampleId("id-1"), "cat",
                                      SampleStatus.NEW, original);

        original[0] = 999.0;
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, s.getFeatures());
    }

    @Test
    void featuresAreCopiedOnGet() {
        DataSample s = sample();
        double[] fromGetter = s.getFeatures();
        fromGetter[0] = 999.0;
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, s.getFeatures());
    }

    // ---------- normalized (доп. часть) ----------

    @Test
    void normalizedReturnsNewObjectAndKeepsOriginal() {
        DataSample s = new DataSample(new SampleId("id-1"), "cat",
                                      SampleStatus.NEW,
                                      new double[]{10.0, 20.0, 30.0});
        DataSample n = s.normalized(10.0, 30.0);

        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, n.getFeatures());
        assertArrayEquals(new double[]{10.0, 20.0, 30.0}, s.getFeatures());
    }

    @Test
    void normalizedRejectsInvalidRange() {
        DataSample s = sample();
        assertThrows(IllegalArgumentException.class, () -> s.normalized(10.0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> s.normalized(30.0, 10.0));
    }
}