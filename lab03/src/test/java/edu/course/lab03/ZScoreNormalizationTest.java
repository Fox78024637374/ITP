package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ZScoreNormalizationTest {

    private static final double EPS = 1e-9;

    @Test
    void computesKnownZScores() {
        ZScoreNormalization strategy = new ZScoreNormalization();
        double[] result = strategy.normalize(new double[]{2.0, 4.0, 4.0, 4.0, 5.0, 5.0, 7.0, 9.0});
        assertArrayEquals(new double[]{-1.5, -0.5, -0.5, -0.5, 0.0, 0.0, 1.0, 2.0}, result, EPS);
    }

    @Test
    void resultHasZeroMeanAndUnitDeviation() {
        ZScoreNormalization strategy = new ZScoreNormalization();
        double[] result = strategy.normalize(new double[]{3.0, 6.0, 9.0, 12.0});

        double sum = 0.0;
        for (double value : result) {
            sum += value;
        }
        double mean = sum / result.length;

        double squaredDifferences = 0.0;
        for (double value : result) {
            double difference = value - mean;
            squaredDifferences += difference * difference;
        }
        double deviation = Math.sqrt(squaredDifferences / result.length);

        assertEquals(0.0, mean, EPS);
        assertEquals(1.0, deviation, EPS);
    }

    @Test
    void constantVectorBecomesZeros() {
        ZScoreNormalization strategy = new ZScoreNormalization();
        double[] result = strategy.normalize(new double[]{4.0, 4.0, 4.0});
        assertArrayEquals(new double[]{0.0, 0.0, 0.0}, result, EPS);
    }

    @Test
    void doesNotModifyInputArray() {
        ZScoreNormalization strategy = new ZScoreNormalization();
        double[] input = {1.0, 2.0, 3.0};
        double[] original = input.clone();

        strategy.normalize(input);

        assertArrayEquals(original, input, EPS);
    }

    @Test
    void handlesEmptyArray() {
        ZScoreNormalization strategy = new ZScoreNormalization();
        assertEquals(0, strategy.normalize(new double[0]).length);
    }

    @Test
    void rejectsNullVector() {
        ZScoreNormalization strategy = new ZScoreNormalization();
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(null));
    }
}
