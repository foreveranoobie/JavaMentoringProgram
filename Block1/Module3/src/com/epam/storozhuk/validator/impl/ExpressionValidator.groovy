package com.epam.storozhuk.validator.impl

import com.epam.storozhuk.validator.Validator

class ExpressionValidator implements Validator {
    @Override
    boolean validate(String expression) {
        return validateForBrackets(expression) && checkExpressionEdges(expression)
    }

    private boolean validateForBrackets(String expression) {
        def leftBracketsCount = 0
        def rightBracketsCount = 0
        for (String symbol : expression.split("")) {
            if (symbol.equals('('))
                leftBracketsCount++
            else if (symbol.equals(')'))
                rightBracketsCount++
        }
        return leftBracketsCount == rightBracketsCount
    }

    private boolean checkExpressionEdges(String expression) {
        return expression.matches("^[\\(\\d][\\d\\(\\)+\\-*/]+[\\)\\d]\$")
    }
}
