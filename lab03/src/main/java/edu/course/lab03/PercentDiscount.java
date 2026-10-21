package edu.course.lab03;

/**
 * Политика процентной скидки.
 */
public final class PercentDiscount implements DiscountPolicy {

    private final double percent;

    /**
     * @param percent размер скидки в процентах, от 0 до 100 включительно
     */
    public PercentDiscount(double percent) {
        if (!Double.isFinite(percent) || percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Процент скидки должен быть от 0 до 100");
        }
        this.percent = percent;
    }

    @Override
    public double apply(double price) {
        requireValidPrice(price);
        return price * (100.0 - percent) / 100.0;
    }

    public double getPercent() {
        return percent;
    }

    private static void requireValidPrice(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Цена должна быть конечной и неотрицательной");
        }
    }
}
