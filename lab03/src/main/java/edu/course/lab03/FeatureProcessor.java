package edu.course.lab03;

/**
 * Обрабатывает один вектор признаков с помощью переданной стратегии нормализации.
 * Класс не создает стратегию внутри себя и не знает ее конкретный тип.
 */
public class FeatureProcessor {

    private final NormalizationStrategy normalizationStrategy;

    public FeatureProcessor(NormalizationStrategy normalizationStrategy) {
        if (normalizationStrategy == null) {
            throw new IllegalArgumentException("Стратегия нормализации не может быть null");
        }
        this.normalizationStrategy = normalizationStrategy;
    }

    public double[] process(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Вектор не может быть null");
        }
        // Копия защищает исходный массив от изменений внутри стратегии.
        double[] safeInput = values.clone();
        return normalizationStrategy.normalize(safeInput);
    }
}
