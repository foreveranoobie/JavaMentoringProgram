package com.epam.storozhuk.calculator.impl

import com.epam.storozhuk.calculator.Calculator
import com.epam.storozhuk.number.Number
import com.epam.storozhuk.parser.impl.MathExpressionParser
import com.epam.storozhuk.validator.impl.ExpressionValidator

import java.util.logging.Logger

class CalculatorImpl implements Calculator {
    private def expressionParser
    private def expressionValidator
    private Map<String, Closure> operations
    private Logger logger;

    CalculatorImpl() {
        expressionParser = new MathExpressionParser()
        expressionValidator = new ExpressionValidator();
        logger = Logger.getLogger(CalculatorImpl.getClass().getName())
        initOperations()
    }

    @Override
    String calculate(String expression) {
        if (!expressionValidator.validate(expression)) {
            throw new IllegalArgumentException("Wrong input for expression ${expression}!");
        }
        List<String> queue = expressionParser.parse(expression)
        def getOperation = operations.&get
        println(queue)
        for (int index = 0; index < queue.size() || queue.size() != 1; index++) {
            while (!isOperation(queue.get(index))) {
                index++
            }
            def firstNum = new BigInteger(queue.get(index - 2))
            def secondNum = new BigInteger(queue.get(index - 1))
            def operationSymbol = queue.get(index)
            queue.set(index - 2, getOperation(operationSymbol).call(firstNum, secondNum).toString())
            queue.removeAt(index)
            queue.removeAt(index - 1)
            index -= 2
            logger.info("Performing operation: ${firstNum} ${operationSymbol} ${secondNum} = ${queue.get(index)}")
        }
        return queue.get(0)
    }

    private boolean isOperation(String symbol) {
        return symbol.matches("[*/+-]")
    }

    private void initOperations() {
        operations = new HashMap<>();
        operations.put("*", (first, second) -> { return first.multiply(second) })
        operations.put("/", (first, second) -> { return first.div(second) })
        operations.put("+", (first, second) -> { return first.plus(second) })
        operations.put("-", (first, second) -> { return first.minus(second) })
    }
}
