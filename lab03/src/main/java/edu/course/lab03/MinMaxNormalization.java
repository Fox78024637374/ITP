package edu.course.lab03;

/**
 * Приводит значения вектора к диапазону [0, 1] по формуле
 * {@code (x - min) / (max - min)}.
 */
public final class MinMaxNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        requireValidVector(values);

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

        double[] normalized = new double[values.length];
        if (max == min) {
            // Все значения одинаковы: диапазон нулевой, поэтому возвращаем нули.
            return normalized;
        }

        for (int i = 0; i < values.length; i++) {
            normalized[i] = (values[i] - min) / (max - min);
        }
        return normalized;
    }

    private static void requireValidVector(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Вектор не может быть null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Вектор не может быть пустым");
        }
        for (double value : values) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Все значения вектора должны быть конечными");
            }
        }
    }
}
