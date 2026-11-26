package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LoadResultTest {

    @TempDir
    Path tempDir;

    private LoadResult validResult() throws IOException {
        Path file = tempDir.resolve("samples.txt");
        Files.write(file, List.of(
                SampleFileLoader.HEADER,
                "S-101;cat;camera-1;128;READY"), StandardCharsets.UTF_8);
        return new SampleFileLoader().load(file);
    }

    @Test
    void storesSamplesAndErrors() throws IOException {
        LoadResult result = validResult();

        assertEquals(1, result.getSamples().size());
        assertEquals(0, result.getErrors().size());
        assertFalse(result.hasErrors());
        assertEquals("S-101", result.getSamples().get(0).getId());
    }

    @Test
    void errorsListIsFilledForInvalidRow() {
        Sample sample = new Sample("S-101", "cat", "camera-1", 128, SampleStatus.READY);
        ValidationError error = new ValidationError(3, "featureCount", "Ожидалось целое число");

        LoadResult result = new LoadResult(List.of(sample), List.of(error));

        assertTrue(result.hasErrors());
        assertEquals("featureCount", result.getErrors().get(0).getField());
        assertEquals(3, result.getErrors().get(0).getLineNumber());
    }

    @Test
    void listsReturnedToCallerAreUnmodifiable() throws IOException {
        LoadResult result = validResult();

        assertThrows(UnsupportedOperationException.class, () -> result.getSamples().clear());
        assertThrows(UnsupportedOperationException.class, () -> result.getErrors().clear());
    }
}
