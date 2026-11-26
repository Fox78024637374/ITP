package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SampleFileLoaderTest {

    @TempDir
    Path tempDir;

    private final SampleFileLoader loader = new SampleFileLoader();

    private Path writeFile(String fileName, String... lines) throws IOException {
        Path file = tempDir.resolve(fileName);
        Files.write(file, List.of(lines), StandardCharsets.UTF_8);
        return file;
    }

    private static ValidationError onlyError(LoadResult result) {
        assertEquals(1, result.getErrors().size(), "ожидалась ровно одна ошибка: " + result.getErrors());
        return result.getErrors().get(0);
    }

    @Test
    void loadsValidFile() throws IOException {
        Path file = writeFile("valid.txt",
                SampleFileLoader.HEADER,
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;64;TRAINING");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(2, result.getSamples().size());

        Sample first = result.getSamples().get(0);
        assertEquals("S-101", first.getId());
        assertEquals("cat", first.getLabel());
        assertEquals("camera-1", first.getSource());
        assertEquals(128, first.getFeatureCount());
        assertEquals(SampleStatus.READY, first.getStatus());
        assertEquals(SampleStatus.TRAINING, result.getSamples().get(1).getStatus());
    }

    @Test
    void loadsCyrillicAndUnicodeText() throws IOException {
        Path file = writeFile("unicode.txt",
                SampleFileLoader.HEADER,
                "S-201;кошка;камера-1;32;READY");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals("кошка", result.getSamples().get(0).getLabel());
        assertEquals("камера-1", result.getSamples().get(0).getSource());
    }

    @Test
    void reportsEmptyField() throws IOException {
        Path file = writeFile("empty-field.txt",
                SampleFileLoader.HEADER,
                "S-101;cat;camera-1;128;READY",
                "S-102;;camera-1;64;READY");

        LoadResult result = loader.load(file);

        assertEquals(1, result.getSamples().size());
        ValidationError error = onlyError(result);
        assertEquals(3, error.getLineNumber());
        assertEquals("label", error.getField());
    }

    @Test
    void reportsFieldWithOnlySpacesAsEmpty() throws IOException {
        Path file = writeFile("blank-field.txt",
                SampleFileLoader.HEADER,
                "S-102;cat;   ;64;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals("source", onlyError(result).getField());
    }

    @Test
    void reportsNonNumericFeatureCount() throws IOException {
        Path file = writeFile("bad-number.txt",
                SampleFileLoader.HEADER,
                "S-102;cat;camera-1;many;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        ValidationError error = onlyError(result);
        assertEquals(2, error.getLineNumber());
        assertEquals("featureCount", error.getField());
    }

    @Test
    void reportsUnknownStatus() throws IOException {
        Path file = writeFile("bad-status.txt",
                SampleFileLoader.HEADER,
                "S-102;cat;camera-1;64;DONE");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        ValidationError error = onlyError(result);
        assertEquals(2, error.getLineNumber());
        assertEquals("status", error.getField());
    }

    @Test
    void reportsNonPositiveFeatureCount() throws IOException {
        Path file = writeFile("non-positive.txt",
                SampleFileLoader.HEADER,
                "S-103;cat;camera-1;0;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals("featureCount", onlyError(result).getField());
    }

    @Test
    void reportsTooFewAndTooManyColumns() throws IOException {
        Path file = writeFile("columns.txt",
                SampleFileLoader.HEADER,
                "S-101;cat;camera-1;128",
                "S-102;cat;camera-1;64;READY;extra");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals(3, result.getErrors().get(1).getLineNumber());
        assertEquals("*", result.getErrors().get(0).getField());
        assertEquals("*", result.getErrors().get(1).getField());
    }

    @Test
    void keepsValidRowsWhenOtherRowsAreInvalid() throws IOException {
        Path file = writeFile("mixed.txt",
                SampleFileLoader.HEADER,
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;many;READY",
                "S-103;fox;camera-3;16;TRAINING",
                "S-104;owl;camera-4;8;UNKNOWN");

        LoadResult result = loader.load(file);

        assertEquals(2, result.getSamples().size());
        assertEquals("S-101", result.getSamples().get(0).getId());
        assertEquals("S-103", result.getSamples().get(1).getId());

        assertEquals(2, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber());
        assertEquals("featureCount", result.getErrors().get(0).getField());
        assertEquals(5, result.getErrors().get(1).getLineNumber());
        assertEquals("status", result.getErrors().get(1).getField());
    }

    @Test
    void skipsBlankLinesAndKeepsRealLineNumbers() throws IOException {
        Path file = writeFile("blank-lines.txt",
                SampleFileLoader.HEADER,
                "",
                "   ",
                "S-102;cat;camera-1;many;READY");

        LoadResult result = loader.load(file);

        assertEquals(1, result.getErrors().size());
        assertEquals(4, onlyError(result).getLineNumber());
    }

    @Test
    void headerOnlyFileGivesEmptyResult() throws IOException {
        Path file = writeFile("header-only.txt", SampleFileLoader.HEADER);

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void ignoresUtf8BomBeforeHeader() throws IOException {
        Path file = tempDir.resolve("bom.txt");
        String content = "\uFEFF" + SampleFileLoader.HEADER + "\nS-101;cat;camera-1;128;READY\n";
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));

        LoadResult result = loader.load(file);

        assertEquals(1, result.getSamples().size());
        assertFalse(result.hasErrors());
    }

    @Test
    void missingFileThrowsDataLoadingExceptionWithCause() {
        Path missing = tempDir.resolve("absent.txt");

        DataLoadingException exception = assertThrows(DataLoadingException.class, () -> loader.load(missing));

        assertTrue(exception.getMessage().contains("absent.txt"), exception.getMessage());
        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof NoSuchFileException,
                "причина должна быть исходным I/O-исключением, получено: " + exception.getCause());
    }

    @Test
    void unreadablePathThrowsDataLoadingExceptionWithCause() throws IOException {
        Path directory = Files.createDirectory(tempDir.resolve("directory.txt"));

        DataLoadingException exception = assertThrows(DataLoadingException.class, () -> loader.load(directory));

        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof IOException);
    }

    @Test
    void invalidHeaderIsSourceError() throws IOException {
        Path file = writeFile("bad-header.txt",
                "id;label;source;count;status",
                "S-101;cat;camera-1;128;READY");

        DataLoadingException exception = assertThrows(DataLoadingException.class, () -> loader.load(file));

        assertTrue(exception.getMessage().contains("заголовок"), exception.getMessage());
    }

    @Test
    void emptyFileIsSourceError() throws IOException {
        Path file = writeFile("empty.txt", "");

        assertThrows(DataLoadingException.class, () -> loader.load(file));
    }

    @Test
    void nullPathIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> loader.load(null));
    }
}
