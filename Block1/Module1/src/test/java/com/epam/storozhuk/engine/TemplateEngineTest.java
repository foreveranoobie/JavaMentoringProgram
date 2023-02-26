package com.epam.storozhuk.engine;

import com.epam.storozhuk.engine.validator.TemplateValidator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.xml.bind.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;

@RunWith(Parameterized.class)
public class TemplateEngineTest {

    private TemplateEngine sut;

    @Mock
    private TemplateValidator templateValidator;

    @BeforeEach
    public void setUp() {
        templateValidator = Mockito.mock(TemplateValidator.class);
        sut = new TemplateEngine(templateValidator);
    }

    @ParameterizedTest
    @MethodSource("provideTemplateWithVariables")
    public void shouldCorrectlyConvertTemplate(String template, String stringVariables,
            String expectedResult) throws ValidationException {
        Mockito.doNothing().when(templateValidator).validateTemplate(Mockito.anyString(), Mockito.anyMap());
        Map<String, String> variables = new LinkedHashMap<>(getVariablesFromString(stringVariables));
        final String resultString = sut.convert(template, variables);
        Assert.assertEquals(resultString, expectedResult);
    }

    private static Stream<Arguments> provideTemplateWithVariables() {
        return Stream.of(Arguments.of("Hello, #{name}!", "name=Bob", "Hello, Bob!"),
                Arguments.of("Hello, #{name}! My name is also #{name}", "name=Bob", "Hello, Bob! My name is also Bob"),
                Arguments.of("Hello, #{name}! My name is #{secondName}", "name=World,secondName=#{John Doe}",
                        "Hello, World! My name is #{John Doe}"));
    }

    private Map<String, String> getVariablesFromString(String inputVars) {
        Map<String, String> variables = new LinkedHashMap<>();
        String[] distinctVariables = inputVars.split(",");
        for (String distinctVariable : distinctVariables) {
            variables.put(distinctVariable.substring(0, distinctVariable.indexOf("=")),
                    distinctVariable.substring(distinctVariable.indexOf("=") + 1));
        }
        return variables;
    }
}

