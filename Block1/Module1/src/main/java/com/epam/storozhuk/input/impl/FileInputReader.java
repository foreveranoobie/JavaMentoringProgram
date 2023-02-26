package com.epam.storozhuk.input.impl;

import com.epam.storozhuk.input.AbstractInputReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class designed specially for reading template from input file
 */
public class FileInputReader extends AbstractInputReader {

    private String inputDir;

    public FileInputReader(String inputDir) {
        this.inputDir = inputDir;
    }

    @Override
    public void readTemplate() throws Exception {
        String input = Files.readAllLines(Paths.get(inputDir)).stream().reduce("", String::concat);
        setTemplate(input);
    }
}
