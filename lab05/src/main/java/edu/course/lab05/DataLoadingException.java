package edu.course.lab05;

/**
 * Ошибка источника: файл отсутствует, недоступен или имеет неверный заголовок.
 * Исходное исключение сохраняется в {@link #getCause()}.
 */
public final class DataLoadingException extends RuntimeException {

    public DataLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
