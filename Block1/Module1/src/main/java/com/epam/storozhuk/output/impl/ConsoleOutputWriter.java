package com.epam.storozhuk.output.impl;

import com.epam.storozhuk.output.OutputWriter;

/**
 * Prints converted template to console
 */
public class ConsoleOutputWriter implements OutputWriter {

    @Override
    public void writeOutput(String result) {
        System.out.println(result);
    }
}
