package edu.course.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CourseToolkitTest {

    @Test
    void returnsTrueForEvenNumber() {
        assertTrue(CourseToolkit.isEven(8));
    }

    @Test
    void returnsFalseForOddNumber() {
        assertFalse(CourseToolkit.isEven(7));
    }

    @Test
    void returnsTrueForZero() {
        assertTrue(CourseToolkit.isEven(0));
    }

    @Test
    void returnsTrueForNegativeEvenNumber() {
        assertTrue(CourseToolkit.isEven(-8));
    }


    @Test
    void isPrimeFalseForNumbersLessThanTwo() {
        assertFalse(CourseToolkit.isPrime(-5));
        assertFalse(CourseToolkit.isPrime(0));
        assertFalse(CourseToolkit.isPrime(1));
    }

    @Test
    void isPrimeTrueForTwo() {
        assertTrue(CourseToolkit.isPrime(2));
    }

    @Test
    void isPrimeFalseForComposite() {
        assertFalse(CourseToolkit.isPrime(4));
        assertFalse(CourseToolkit.isPrime(100));
    }

    @Test
    void isPrimeFalseForSquareOfPrime() {
        assertFalse(CourseToolkit.isPrime(9));   // 3*3
        assertFalse(CourseToolkit.isPrime(49));  // 7*7
    }

    @Test
    void isPrimeTrueForLargePrime() {
        assertTrue(CourseToolkit.isPrime(97));
    }


    @Test
    void isPalindromeTrueForSimpleWord() {
        assertTrue(CourseToolkit.isPalindrome("level"));
    }

    @Test
    void isPalindromeIsCaseSensitive() {
        assertFalse(CourseToolkit.isPalindrome("Level"));
    }

    @Test
    void isPalindromeTreatsSpacesAsSignificant() {
        assertFalse(CourseToolkit.isPalindrome("а роза упала на лапу азора"));
    }

    @Test
    void isPalindromeThrowsOnNull() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.isPalindrome(null));
    }

    @Test
    void averageReturnsFractionalResult() {
        assertEquals(1.5, CourseToolkit.average(new int[]{1, 2}));
    }

    @Test
    void averageDoesNotModifyArray() {
        int[] input = {1, 2, 3};
        CourseToolkit.average(input);
        assertArrayEquals(new int[]{1, 2, 3}, input);
    }

    @Test
    void averageThrowsOnNull() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(null));
    }

    @Test
    void averageThrowsOnEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(new int[]{}));
    }

    @Test
    void minReturnsSmallestElement() {
        assertEquals(1, CourseToolkit.min(new int[]{3, 1, 4, 1, 5, 9, 2, 6}));
    }

    @Test
    void minWorksWithSingleElement() {
        assertEquals(42, CourseToolkit.min(new int[]{42}));
    }

    @Test
    void minWorksWhenAllEqual() {
        assertEquals(7, CourseToolkit.min(new int[]{7, 7, 7, 7}));
    }

    @Test
    void minWorksWithNegatives() {
        assertEquals(-100, CourseToolkit.min(new int[]{-3, -100, -50, 0}));
    }

    @Test
    void minFindsElementAtStart() {
        assertEquals(-5, CourseToolkit.min(new int[]{-5, 10, 20}));
    }

    @Test
    void minFindsElementAtEnd() {
        assertEquals(-5, CourseToolkit.min(new int[]{10, 20, -5}));
    }

    @Test
    void minThrowsOnNull() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.min(null));
    }

    @Test
    void minThrowsOnEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.min(new int[]{}));
    }

    @Test
    void minDoesNotModifyArray() {
        int[] input = {3, 1, 4, 1, 5};
        CourseToolkit.min(input);
        assertArrayEquals(new int[]{3, 1, 4, 1, 5}, input);
    }


    @Test
    void maxReturnsLargestElement() {
        assertEquals(9, CourseToolkit.max(new int[]{3, 1, 4, 1, 5, 9, 2, 6}));
    }

    @Test
    void maxWorksWithSingleElement() {
        assertEquals(42, CourseToolkit.max(new int[]{42}));
    }

    @Test
    void maxWorksWhenAllEqual() {
        assertEquals(7, CourseToolkit.max(new int[]{7, 7, 7, 7}));
    }

    @Test
    void maxWorksWithNegatives() {
        assertEquals(0, CourseToolkit.max(new int[]{-3, -100, -50, 0}));
    }

    @Test
    void maxFindsElementAtStart() {
        assertEquals(100, CourseToolkit.max(new int[]{100, 10, 20}));
    }

    @Test
    void maxFindsElementAtEnd() {
        assertEquals(100, CourseToolkit.max(new int[]{10, 20, 100}));
    }

    @Test
    void maxThrowsOnNull() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.max(null));
    }

    @Test
    void maxThrowsOnEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.max(new int[]{}));
    }




}
