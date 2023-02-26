package com.epam.storozhuk.output;

import com.epam.storozhuk.output.impl.ConsoleOutputWriter;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConsoleOutputWriterTest {

    private ConsoleOutputWriter sut;

    @Test
    public void shouldPrintTemplateToConsole() {
        PrintStream out = Mockito.spy(new PrintStream(System.out));
        String outputTemplate = "Hello, World!";
        sut = new ConsoleOutputWriter();
        System.setOut(out);
        sut.writeOutput(outputTemplate);
        Mockito.verify(out).println(Mockito.eq(outputTemplate));
    }
}
