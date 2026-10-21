package edu.course.lab03;

/**
 * Стратегия расчета скидки для цены.
 */
public interface DiscountPolicy {

    /**
     * Применяет скидку к исходной цене.
     *
     * @param price исходная цена, неотрицательная и конечная
     * @return цена после применения скидки
     */
    double apply(double price);
}
