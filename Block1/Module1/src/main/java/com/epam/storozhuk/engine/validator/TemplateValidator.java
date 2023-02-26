package com.epam.storozhuk.engine.validator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.ValidationException;

/**
 * The class responsible for validation of template before it's conversion to normal string
 */
public class TemplateValidator {

    /**
     *
     * @param template initial template to validate
     * @param values values taken from user, given to check for placeholders correspondences
     * @throws ValidationException thrown in case of missing values
     */
    public void validateTemplate(String template, Map<String, String> values) throws ValidationException {
        Pattern pattern = Pattern
                .compile("(#\\{([^(#\\{)])*\\})", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(template);
        List<String> templateValues = new LinkedList<>();
        while (matcher.find()) {
            templateValues.add(matcher.group());
        }
        String unknownValue;
        try {
            unknownValue = templateValues.stream()
                    .filter(key -> !values.containsKey(key.substring(2, key.length() - 1)))
                    .findAny()
                    .get();
        } catch (NoSuchElementException ex) {
            return;
        }
        throw new ValidationException(String.format("The key %s from template is not present in values", unknownValue));
    }

}
