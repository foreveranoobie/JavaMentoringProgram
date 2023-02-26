package com.epam.storozhuk.input;

import java.util.Map;

/**
 * Basic interface to declare the contract of reading the input with templates
 */
public interface InputReader {

    /**
     * Reads input to template
     * @throws Exception in case of having problems while reading
     */
    void readTemplate() throws Exception;

    /**
     * Reads variables
     */
    void readInputVariables();

    /**
     *
     * @return template previously processed from the input source
     */
    String getTemplate();

    /**
     *
     * @return Map of variables entered by user
     */
    Map<String, String> getVariables();

    /**
     * Sets the template
     * @param template The raw template to be processed
     */
    void setTemplate(String template);
}
