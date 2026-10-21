package edu.course.lab03;

/**
 * Политика без скидки: возвращает исходную цену.
 */
public final class NoDiscount implements DiscountPolicy {

    @Override
    public double apply(double price) {
        requireValidPrice(price);
        return price;
    }

    private static void requireValidPrice(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Цена должна быть конечной и неотрицательной");
        }
    }
}
