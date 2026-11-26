package edu.course.lab05;

/**
 * Ошибка одной записи файла: номер строки, проблемное поле и пояснение.
 * Поле {@code field} содержит {@code "*"}, если ошибка относится ко всей строке.
 */
public final class ValidationError {

    private final int lineNumber;
    private final String field;
    private final String message;

    public ValidationError(int lineNumber, String field, String message) {
        if (lineNumber <= 0) {
            throw new IllegalArgumentException("Номер строки должен быть положительным");
        }
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("Имя поля не может быть null или пустым");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Сообщение об ошибке не может быть null или пустым");
        }
        this.lineNumber = lineNumber;
        this.field = field;
        this.message = message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "строка " + lineNumber + ", поле '" + field + "': " + message;
    }
}
