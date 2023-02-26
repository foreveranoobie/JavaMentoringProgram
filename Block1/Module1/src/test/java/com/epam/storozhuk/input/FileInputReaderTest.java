package com.epam.storozhuk.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.storozhuk.input.impl.FileInputReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileInputReaderTest {

    private FileInputReader sut;
    private final String tempInputFileName = "input.txt";
    private final String tempOutputFileName = "output.txt";

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void shouldSuccessfullyReadTemplate(@TempDir Path tempInputDir) throws Exception {
        File inputFile = new File(tempInputDir.toFile().getAbsolutePath() + "/" + tempInputFileName);
        inputFile.createNewFile();
        String expectedTemplate = "Hello, #{name}! My name is #{personName}";
        writeTemplateToFile(inputFile, expectedTemplate);
        sut = new FileInputReader(inputFile.getAbsolutePath());
        sut.readTemplate();
        assertEquals(sut.getTemplate(), expectedTemplate);
    }

    private void writeTemplateToFile(File tempInput, String text) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(tempInput)) {
            fos.write(text.getBytes(StandardCharsets.UTF_8));
        }
    }
}
