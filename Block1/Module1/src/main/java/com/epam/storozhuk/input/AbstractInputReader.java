package com.epam.storozhuk.input;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Abstract class made in order to implement reading variables from the console
 */
public abstract class AbstractInputReader implements InputReader {

    private String template;
    private Map<String, String> variables;

    public AbstractInputReader() {
        variables = new LinkedHashMap<>();
    }

    public abstract void readTemplate() throws Exception;

    /**
     * Method is designed specially for reading variables from the user's console
     */
    public void readInputVariables() {
        System.out.println("Enter variables and values (ex. name1=value1,name2=value2)");
        Scanner scanner = new Scanner(System.in);
        String result = scanner.nextLine();
        String[] distinctVariables = result.split(",");
        for (String distinctVariable : distinctVariables) {
            variables.put(distinctVariable.substring(0, distinctVariable.indexOf("=")),
                    distinctVariable.substring(distinctVariable.indexOf("=") + 1));
        }
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, String> getVariables() {
        return variables;
    }
}
