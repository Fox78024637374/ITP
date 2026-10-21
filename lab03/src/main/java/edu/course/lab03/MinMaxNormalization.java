package edu.course.lab03;

/**
 * Приводит значения вектора к диапазону [0, 1] по формуле
 * {@code (x - min) / (max - min)}.
 */
public class MinMaxNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Вектор не может быть null");
        }
        if (values.length == 0) {
            return new double[0];
        }

        double min = values[0];
        double max = values[0];
        for (double value : values) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        double[] result = new double[values.length];
        if (max == min) {
            // Все значения одинаковы: диапазон нулевой, поэтому возвращаем нули.
            return result;
        }

        double range = max - min;
        for (int i = 0; i < values.length; i++) {
            result[i] = (values[i] - min) / range;
        }
        return result;
    }
}
