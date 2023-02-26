package com.epam.storozhuk.input;

import com.epam.storozhuk.input.impl.ConsoleInputReader;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


public class InputReaderTest {

    private InputReader inputReader;

    @BeforeEach
    public void setUp() {
        inputReader = new ConsoleInputReader();
    }

    @Test
    public void shouldSuccessfullyReadVariables() {
        String initialTemplate = "Hello, #{name}!";
        String initialVariables = "name=World";
        inputReader.setTemplate(initialTemplate);
        System.setIn(new ByteArrayInputStream(initialVariables.getBytes(StandardCharsets.UTF_8)));
        inputReader.readInputVariables();
        Map<String, String> resultVariables = inputReader.getVariables();
        Assert.assertTrue(resultVariables.size() == 1);
        Assert.assertTrue(resultVariables.containsKey("name"));
        Assert.assertEquals(resultVariables.get("name"), "World");
    }
}
