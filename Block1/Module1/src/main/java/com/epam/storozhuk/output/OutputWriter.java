package com.epam.storozhuk.output;

import java.io.IOException;

/**
 * Basic interface to declare the contract of writing the output
 */
public interface OutputWriter {

    /**
     *
     * @param result string what should be written to it's destination
     * @throws IOException in case of any problems caused while saving to output
     */
    void writeOutput(String result) throws IOException;
}
