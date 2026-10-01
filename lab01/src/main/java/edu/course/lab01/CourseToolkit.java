package edu.course.lab01;

/**
 * Небольшие методы для первой лабораторной работы.
 */
public final class CourseToolkit {

    private CourseToolkit() {
        // Утилитарный класс не должен иметь экземпляров.
    }

    /**
     * Возвращает true, если число четное.
     */
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }


    public static boolean isPrime(int number){
        if (number < 2) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean isPalindrome(String text){
        if (text == null) {
            throw new IllegalArgumentException();
        }
        int l = 0;
        int r = text.length() - 1;
        while (l < r) {
            if (text.charAt(l) != text.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }




    public static double average(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException();
        }

        int sum = 0;
        for (int num : values) {
            sum += num;
        }
        return (double) sum / values.length;
        }

    
    public static int min(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException();
        }
        if (values.length == 0) {
            throw new IllegalArgumentException();
        }

        int result = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < result) {
                result = values[i];
            }
        }
        return result;
    }

    public static int max(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException();
        }
        if (values.length == 0) {
            throw new IllegalArgumentException();
        }

        int result = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > result) {
                result = values[i];
            }
        }
        return result;
    }


}