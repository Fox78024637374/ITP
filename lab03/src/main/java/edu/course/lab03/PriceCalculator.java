package edu.course.lab03;

/**
 * Считает итоговую цену, используя переданную стратегию скидки.
 */
public final class PriceCalculator {

    private final DiscountPolicy discountPolicy;

    public PriceCalculator(DiscountPolicy discountPolicy) {
        if (discountPolicy == null) {
            throw new IllegalArgumentException("Политика скидки не может быть null");
        }
        this.discountPolicy = discountPolicy;
    }

    public double calculate(double price) {
        return discountPolicy.apply(price);
    }
}
