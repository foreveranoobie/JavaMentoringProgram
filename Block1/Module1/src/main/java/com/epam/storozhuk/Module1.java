package com.epam.storozhuk;

import com.epam.storozhuk.engine.TemplateEngine;
import com.epam.storozhuk.engine.validator.TemplateValidator;
import com.epam.storozhuk.input.InputReader;
import com.epam.storozhuk.input.impl.ConsoleInputReader;
import com.epam.storozhuk.input.impl.FileInputReader;
import com.epam.storozhuk.output.OutputWriter;
import com.epam.storozhuk.output.impl.ConsoleOutputWriter;
import com.epam.storozhuk.output.impl.FileOutputWriter;


/**
 * Entry-point of application
 */
public class Module1 {

    /**
     * @param args Arguments taken from CMD in case of reading template from File
     * @throws Exception in case of having validation fails or working with input files
     */
    public static void main(String[] args) throws Exception {
        InputReader inputReader;
        OutputWriter outputWriter;
        if (args.length == 2) {
            inputReader = new FileInputReader(args[0]);
            outputWriter = new FileOutputWriter(args[1]);
        } else {
            inputReader = new ConsoleInputReader();
            outputWriter = new ConsoleOutputWriter();
        }
        inputReader.readTemplate();
        inputReader.readInputVariables();
        TemplateEngine templateEngine = new TemplateEngine(new TemplateValidator());
        String result = templateEngine.convert(inputReader.getTemplate(), inputReader.getVariables());
        outputWriter.writeOutput(result);
    }
}
