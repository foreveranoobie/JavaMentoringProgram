package com.epam.storozhuk.it;

import com.epam.storozhuk.engine.TemplateEngine;
import com.epam.storozhuk.engine.validator.TemplateValidator;
import com.epam.storozhuk.extension.FileLoggingExtension;
import com.epam.storozhuk.input.InputReader;
import com.epam.storozhuk.input.impl.ConsoleInputReader;
import com.epam.storozhuk.input.impl.FileInputReader;
import com.epam.storozhuk.meta.IntegrationTest;
import com.epam.storozhuk.output.OutputWriter;
import com.epam.storozhuk.output.impl.ConsoleOutputWriter;
import com.epam.storozhuk.output.impl.FileOutputWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

@IntegrationTest
@ExtendWith(FileLoggingExtension.class)
public class TemplateEngineIT {

    private TemplateEngine sut;
    private InputReader inputReader;
    private OutputWriter outputWriter;
    private final String inputTemplate = "Hello, #{name}! My name is #{secondName}";
    private final String inputParameters = "name=World,secondName=#{John Doe}";
    private final String expectedResult = "Hello, World! My name is #{John Doe}";

    @BeforeEach
    public void setUp() {
        sut = new TemplateEngine(new TemplateValidator());
    }

    @Test
    public void shouldSuccessfullyConvertTemplateAndPrintToConsole() throws Exception {
        inputReader = new ConsoleInputReader();
        outputWriter = new ConsoleOutputWriter();
        PrintStream out = Mockito.spy(new PrintStream(System.out));

        System.setIn(new ByteArrayInputStream(inputTemplate.getBytes(StandardCharsets.UTF_8)));
        inputReader.readTemplate();

        System.setIn(new ByteArrayInputStream(inputParameters.getBytes(StandardCharsets.UTF_8)));
        inputReader.readInputVariables();
        String result = sut.convert(inputReader.getTemplate(), inputReader.getVariables());

        Assertions.assertEquals(expectedResult, result);

        System.setOut(out);
        outputWriter.writeOutput(result);

        Mockito.verify(out).println(Mockito.eq(expectedResult));
    }

    @Test
    public void shouldSuccessfullyConvertTemplateAndWriteToFile(@TempDir File tempDir) throws Exception {
        String tempInputFileName = "input.txt";
        String tempOutputFileName = "output.txt";

        File inputFile = new File(tempDir.getAbsolutePath() + "/" + tempInputFileName);
        inputFile.createNewFile();

        File outputFile = new File(tempDir.getAbsolutePath() + "/" + tempOutputFileName);
        inputFile.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(inputFile)) {
            fos.write(inputTemplate.getBytes(StandardCharsets.UTF_8));
        }

        inputReader = new FileInputReader(inputFile.getAbsolutePath());
        outputWriter = new FileOutputWriter(outputFile.getAbsolutePath());

        inputReader.readTemplate();
        Assertions.assertEquals(inputTemplate, inputReader.getTemplate());

        System.setIn(new ByteArrayInputStream(inputParameters.getBytes(StandardCharsets.UTF_8)));
        inputReader.readInputVariables();

        String result = sut.convert(inputReader.getTemplate(), inputReader.getVariables());

        Assertions.assertEquals(expectedResult, result);

        outputWriter.writeOutput(result);

        Assertions.assertEquals(expectedResult, Files.readAllLines(Paths.get(outputFile.getAbsolutePath()))
                .stream()
                .reduce("", String::concat));
    }
}
