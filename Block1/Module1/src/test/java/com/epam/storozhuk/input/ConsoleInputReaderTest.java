package com.epam.storozhuk.input;

import com.epam.storozhuk.input.impl.ConsoleInputReader;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsoleInputReaderTest {

    private ConsoleInputReader sut;

    @Test
    public void shouldSuccessfullyReadTemplate() throws Exception {
        sut = new ConsoleInputReader();
        String initialTemplate = "Hello, #{name}!";
        sut.setTemplate(initialTemplate);
        System.setIn(new ByteArrayInputStream(initialTemplate.getBytes(StandardCharsets.UTF_8)));
        sut.readTemplate();
        Assertions.assertEquals(sut.getTemplate(), initialTemplate);
    }

}
