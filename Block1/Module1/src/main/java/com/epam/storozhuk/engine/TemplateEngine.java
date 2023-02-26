package com.epam.storozhuk.engine;

import com.epam.storozhuk.engine.validator.TemplateValidator;
import java.util.Map;
import javax.xml.bind.ValidationException;

/**
 * Class responsible for template conversion
 */
public class TemplateEngine {

    private TemplateValidator templateValidator;

    public TemplateEngine(TemplateValidator templateValidator) {
        this.templateValidator = templateValidator;
    }

    /**
     *
     * @param template initial template to be converted
     * @param variables variable values to be placed instead of simple placeholders
     * @return fully-converted template according to the given variables
     * @throws ValidationException in case of validation fail when some of the values are not given by user
     */
    public String convert(String template, Map<String, String> variables) throws ValidationException {
        templateValidator.validateTemplate(template, variables);
        StringBuilder result = new StringBuilder(template);
        variables.forEach((key, value) -> {
            int valLength = key.length() + 3;
            int beginIndex = result.indexOf(String.format("#{%s}", key));
            while (beginIndex != -1) {
                result.replace(beginIndex, beginIndex + valLength, value);
                beginIndex = result.indexOf(String.format("#{%s}", key));
            }
        });
        return result.toString();
    }
}
