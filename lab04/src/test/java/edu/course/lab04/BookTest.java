package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BookTest {

    private static final String ISBN = "978-5-4461-0996-8";

    @Test
    void equalBooksHaveSameHashCode() {
        Book first = new Book(ISBN, "Чистый код");
        Book second = new Book(ISBN, "Другое издание");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book first = new Book("978-5-4461-0996-8", "Книга");
        Book second = new Book("978-5-4461-0997-5", "Книга");

        assertNotEquals(first, second);
    }

    @Test
    void hashMapFindsValueByEqualBookWithSameIsbn() {
        Map<Book, String> shelf = new HashMap<>();
        shelf.put(new Book(ISBN, "Чистый код"), "на полке");

        Book lookup = new Book(ISBN, "Совсем другое название");
        assertEquals("на полке", shelf.get(lookup));
    }

    @Test
    void titleDoesNotParticipateInEquality() {
        Book first = new Book(ISBN, "Первое название");
        Book second = new Book(ISBN, "Второе название");

        Map<Book, String> shelf = new HashMap<>();
        shelf.put(first, "на полке");

        assertEquals("на полке", shelf.get(second));
    }

    @Test
    void equalsReturnsFalseForNullAndOtherType() {
        Book book = new Book(ISBN, "Чистый код");

        assertFalse(book.equals(null));
        assertFalse(book.equals("978-5-4461-0996-8"));
    }

    @Test
    void constructorRejectsBlankFields() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null, "Книга"));
        assertThrows(IllegalArgumentException.class, () -> new Book(" ", "Книга"));
        assertThrows(IllegalArgumentException.class, () -> new Book(ISBN, null));
        assertThrows(IllegalArgumentException.class, () -> new Book(ISBN, " "));
    }

    @Test
    void trimsFields() {
        Book book = new Book("  " + ISBN + "  ", "  Чистый код  ");

        assertEquals(ISBN, book.getIsbn());
        assertEquals("Чистый код", book.getTitle());
    }
}
