package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Загрузчик файлов формата {@code id;label;source;featureCount;status}.
 *
 * <p>Ошибки разделены на два вида:
 * <ul>
 *   <li>ошибка источника — файл отсутствует, недоступен или имеет неверный заголовок:
 *       выбрасывается {@link DataLoadingException} с сохраненной причиной;</li>
 *   <li>ошибка записи — одна строка с неверным числом полей, пустым полем,
 *       нечисловым значением или неизвестным статусом: строка пропускается,
 *       а {@link ValidationError} добавляется в результат, чтение продолжается.</li>
 * </ul>
 *
 * <p>Класс ничего не печатает и не вызывает {@code printStackTrace}.
 */
public final class SampleFileLoader {

    /** Ожидаемое число полей в строке данных. */
    static final int FIELD_COUNT = 5;

    /** Ожидаемая первая непустая строка файла. */
    static final String HEADER = "id;label;source;featureCount;status";

    private static final String[] FIELD_NAMES = {"id", "label", "source", "featureCount", "status"};

    private static final String WHOLE_ROW = "*";

    /**
     * Читает файл целиком в UTF-8 и возвращает корректные объекты вместе с ошибками записей.
     *
     * @param path путь к файлу
     * @return список корректных объектов и список ошибок строк
     * @throws DataLoadingException если файл нельзя прочитать или заголовок неверен
     */
    public LoadResult load(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Путь не может быть null");
        }
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException cause) {
            throw new DataLoadingException("Не удалось прочитать файл " + path, cause);
        }

        List<Sample> samples = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();
        int headerLine = -1;
        for (int index = 0; index < lines.size(); index++) {
            int lineNumber = index + 1;
            String line = lines.get(index);
            if (lineNumber == 1 && line.startsWith("\uFEFF")) {
                line = line.substring(1);
            }
            if (line.isBlank()) {
                continue;
            }
            if (headerLine < 0) {
                checkHeader(path, lineNumber, line);
                headerLine = lineNumber;
                continue;
            }
            parseRow(line, lineNumber, samples, errors);
        }
        if (headerLine < 0) {
            throw new DataLoadingException("Файл " + path + " пуст: отсутствует заголовок", null);
        }
        return new LoadResult(samples, errors);
    }

    private void checkHeader(Path path, int lineNumber, String line) {
        String actual = line.trim();
        if (!actual.equalsIgnoreCase(HEADER)) {
            throw new DataLoadingException("Файл " + path + ", строка " + lineNumber
                    + ": ожидался заголовок \"" + HEADER + "\", получено \"" + actual + "\"", null);
        }
    }

    private void parseRow(String line, int lineNumber, List<Sample> samples, List<ValidationError> errors) {
        String[] fields = line.split(";", -1);
        if (fields.length != FIELD_COUNT) {
            errors.add(new ValidationError(lineNumber, WHOLE_ROW,
                    "Ожидалось " + FIELD_COUNT + " полей, получено " + fields.length));
            return;
        }
        for (int field = 0; field < FIELD_COUNT; field++) {
            fields[field] = fields[field].trim();
            if (fields[field].isEmpty()) {
                errors.add(new ValidationError(lineNumber, FIELD_NAMES[field], "Поле пустое"));
                return;
            }
        }

        int featureCount;
        try {
            featureCount = Integer.parseInt(fields[3]);
        } catch (NumberFormatException cause) {
            errors.add(new ValidationError(lineNumber, FIELD_NAMES[3],
                    "Ожидалось целое число, получено \"" + fields[3] + "\""));
            return;
        }
        if (featureCount <= 0) {
            errors.add(new ValidationError(lineNumber, FIELD_NAMES[3],
                    "Количество признаков должно быть положительным, получено " + featureCount));
            return;
        }

        SampleStatus status;
        try {
            status = SampleStatus.valueOf(fields[4].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException cause) {
            errors.add(new ValidationError(lineNumber, FIELD_NAMES[4],
                    "Неизвестный статус \"" + fields[4] + "\""));
            return;
        }

        samples.add(new Sample(fields[0], fields[1], fields[2], featureCount, status));
    }
}
