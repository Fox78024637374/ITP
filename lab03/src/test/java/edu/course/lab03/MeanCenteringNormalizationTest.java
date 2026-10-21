package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MeanCenteringNormalizationTest {

    private static final double EPS = 1e-9;

    @Test
    void subtractsMeanFromEveryValue() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] result = strategy.normalize(new double[]{1.0, 2.0, 3.0});
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, result, EPS);
    }

    @Test
    void resultHasZeroMean() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] result = strategy.normalize(new double[]{4.0, 8.0, 15.0, 16.0, 23.0, 42.0});

        double sum = 0.0;
        for (double value : result) {
            sum += value;
        }
        assertEquals(0.0, sum / result.length, EPS);
    }

    @Test
    void constantVectorBecomesZeros() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] result = strategy.normalize(new double[]{5.0, 5.0, 5.0});
        assertArrayEquals(new double[]{0.0, 0.0, 0.0}, result, EPS);
    }

    @Test
    void doesNotModifyInputArray() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] input = {1.0, 2.0, 3.0};
        double[] original = input.clone();

        strategy.normalize(input);

        assertArrayEquals(original, input, EPS);
    }

    @Test
    void rejectsNullOrEmptyVector() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(null));
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[]{}));
    }
}
