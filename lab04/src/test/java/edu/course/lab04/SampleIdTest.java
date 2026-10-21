package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class SampleIdTest {

    @Test
    void equalWhenDatasetAndNumberMatch() {
        SampleId first = new SampleId("iris", 7);
        SampleId second = new SampleId("iris", 7);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentObservationNumberMeansDifferentKey() {
        assertNotEquals(new SampleId("iris", 1), new SampleId("iris", 2));
    }

    @Test
    void differentDatasetMeansDifferentKey() {
        assertNotEquals(new SampleId("iris", 1), new SampleId("wine", 1));
    }

    @Test
    void equalsReturnsFalseForNullAndOtherType() {
        SampleId id = new SampleId("iris", 1);

        assertFalse(id.equals(null));
        assertFalse(id.equals("iris:1"));
    }

    @Test
    void trimsDatasetName() {
        assertEquals(new SampleId("iris", 1), new SampleId("  iris  ", 1));
        assertEquals("iris", new SampleId("  iris  ", 1).getDatasetName());
    }

    @Test
    void constructorRejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId(null, 1));
        assertThrows(IllegalArgumentException.class, () -> new SampleId(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> new SampleId("iris", -1));
    }
}
