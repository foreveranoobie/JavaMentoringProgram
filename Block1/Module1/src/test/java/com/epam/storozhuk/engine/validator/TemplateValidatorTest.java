package com.epam.storozhuk.engine.validator;

import static com.epam.storozhuk.engine.util.TemplateUtil.generateMapOfValues;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import javax.xml.bind.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class TemplateValidatorTest {

    private TemplateValidator sut;

    @BeforeEach
    public void setUp() {
        sut = new TemplateValidator();
    }

    @TestFactory
    public Collection<DynamicTest> dynamicTestsToCheckValidationCorrectness() {
        return Arrays.asList(dynamicTest("shouldCorrectlyValidateTemplate", () -> {
            String template = "Hello, #{name}! My name is also #{name}";
            Map<String, String> values = generateMapOfValues(new String[]{"name"}, new String[]{"World"});
            Assertions.assertDoesNotThrow(() -> sut.validateTemplate(template, values));
        }), dynamicTest("shouldThrowExceptionOnValidation", () -> {
            String template = "Hello, #{name}! My name is #{myName}";
            Map<String, String> values = generateMapOfValues(new String[]{"name"}, new String[]{"World"});
            values.put("name", "World");
            Assertions.assertThrows(ValidationException.class, () -> sut.validateTemplate(template, values));
        }), dynamicTest("shouldValidateSuccessfullyOnRedundantVariables", () -> {
            String template = "Hello, #{name}!";
            Map<String, String> values = generateMapOfValues(new String[]{"name", "secondName"},
                    new String[]{"World", "Bob"});
            Assertions.assertDoesNotThrow(() -> sut.validateTemplate(template, values));
        }), dynamicTest("shouldSuccessfullyValidateValuesWithPlaceholders", () -> {
            String template = "Hello, #{name}!";
            Map<String, String> values = generateMapOfValues(new String[]{"name"},
                    new String[]{"#{World}"});
            Assertions.assertDoesNotThrow(() -> sut.validateTemplate(template, values));
        }));
    }
}
