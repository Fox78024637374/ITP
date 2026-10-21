package edu.course.lab03;

/**
 * Дополнительная стратегия: z-score нормализация
 * {@code (x - mean) / standardDeviation}.
 */
public class ZScoreNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Вектор не может быть null");
        }
        if (values.length == 0) {
            return new double[0];
        }

        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        double mean = sum / values.length;

        double variance = 0.0;
        for (double value : values) {
            double difference = value - mean;
            variance += difference * difference;
        }
        variance /= values.length;
        double deviation = Math.sqrt(variance);

        double[] result = new double[values.length];
        if (deviation == 0.0) {
            // Все значения одинаковы: стандартное отклонение нулевое.
            return result;
        }

        for (int i = 0; i < values.length; i++) {
            result[i] = (values[i] - mean) / deviation;
        }
        return result;
    }
}
