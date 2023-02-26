package com.epam.storozhuk.output;

import com.epam.storozhuk.output.impl.FileOutputWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

public class FileOutputWriterTest {

    private FileOutputWriter sut;
    private final String outputFileName = "output.txt";

    @Test
    @DisabledOnOs(OS.WINDOWS)
    @Tag("Slow")
    public void testOnSuccessfullySavedTemplate(@TempDir File outputFileDir) throws IOException {
        File outputFile = new File(outputFileDir.getAbsolutePath() + "/" + outputFileName);
        outputFile.createNewFile();
        sut = new FileOutputWriter(outputFile.getAbsolutePath());
        String expectedResultString = "Hello, World!";
        sut.writeOutput(expectedResultString);
        Assertions.assertEquals(expectedResultString, Files.readAllLines(Paths.get(outputFile.getAbsolutePath()))
                .stream()
                .reduce("", String::concat));
    }

}
