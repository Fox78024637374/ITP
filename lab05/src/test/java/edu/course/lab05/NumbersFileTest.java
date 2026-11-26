package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Подготовительная часть: чтение UTF-8 файла, преобразование строк в числа
 * и отдельная проверка исключения для некорректной строки.
 */
class NumbersFileTest {

    @TempDir
    Path tempDir;

    private List<String> numbersFile() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);
        return Files.readAllLines(file, StandardCharsets.UTF_8);
    }

    @Test
    void readsAllLinesFromUtf8File() throws IOException {
        List<String> lines = numbersFile();

        assertEquals(3, lines.size());
        assertEquals(List.of("10", "bad", "20"), lines);
    }

    @Test
    void convertsLinesToIntegersInPlainLoop() throws IOException {
        Path file = tempDir.resolve("valid.txt");
        Files.write(file, List.of("10", "20"), StandardCharsets.UTF_8);

        List<Integer> numbers = new ArrayList<>();
        for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
            numbers.add(Integer.parseInt(line));
        }

        assertEquals(List.of(10, 20), numbers);
    }

    @Test
    void badLineCausesNumberFormatException() throws IOException {
        List<String> lines = numbersFile();

        assertThrows(NumberFormatException.class, () -> {
            for (String line : lines) {
                Integer.parseInt(line);
            }
        });
    }
}
