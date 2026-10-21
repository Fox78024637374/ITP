package edu.course.lab03;

/**
 * Стратегия нормализации одного вектора признаков.
 */
public interface NormalizationStrategy {

    /**
     * Возвращает новый нормализованный вектор, не изменяя исходный массив.
     *
     * @param values исходный вектор, не null; пустой массив возвращается пустым
     * @return новый массив с нормализованными значениями
     */
    double[] normalize(double[] values);
}
