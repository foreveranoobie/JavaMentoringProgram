package com.epam.storozhuk.parser.impl

import com.epam.storozhuk.lexem.Lexem
import com.epam.storozhuk.parser.ExpressionParser

class MathExpressionParser implements ExpressionParser {
    private List operatorsStack;
    private Map<String, Lexem> operators;
    private Map<Lexem, Closure> operatorParsers;
    private List<String> rpnQueue;

    MathExpressionParser() {
        operatorsStack = new ArrayList<String>()
        initOperators();
        initOperatorParsers();
    }

    @Override
    List<String> parse(String expression) {
        rpnQueue = new LinkedList<String>()
        def splitExpression = expression.split("")
        def getOperatorType = operators.&get
        def getParserByOperator = operatorParsers.&get
        def addToQueue = rpnQueue.&add
        for (def sym = 0; sym < splitExpression.length; sym++) {
            if (isNumber(splitExpression[sym])) {
                StringBuilder number = new StringBuilder("")
                while (sym < splitExpression.length && isNumber(splitExpression[sym])) {
                    number.append(splitExpression[sym++])
                }
                addToQueue(number.toString())
                if (sym == splitExpression.length) {
                    break
                }
            }
            getParserByOperator(getOperatorType(splitExpression[sym])).call(splitExpression[sym])
        }
        while (!operatorsStack.isEmpty()) {
            addToQueue(popFromOperatorsStack())
        }
        return rpnQueue
    }

    private boolean isNumber(String expression) {
        return expression.matches("[0-9]");
    }

    private void initOperatorParsers() {
        operatorParsers = new HashMap<>();

        operatorParsers.put(Lexem.FIRST_ORDER_OPERATION, (symbol) -> {
            while (!operatorsStack.isEmpty() && operatorsStack.get(operatorsStack.size() - 1).matches("[*/]")) {
                rpnQueue.add(popFromOperatorsStack())
            }
            pushToOperatorsStack(symbol)
        })

        operatorParsers.put(Lexem.SECOND_ORDER_OPERATION, (symbol) -> {
            while (!operatorsStack.isEmpty() && operatorsStack.get(operatorsStack.size() - 1).matches("[+-]")) {
                rpnQueue.add(popFromOperatorsStack())
            }
            pushToOperatorsStack(symbol)
        })

        operatorParsers.put(Lexem.BRACKET, (symbol) -> {
            if (symbol.equals("(")) {
                pushToOperatorsStack(symbol)
            } else {
                while (!operatorsStack.isEmpty() && !operatorsStack.get(operatorsStack.size() - 1).equals("(")) {
                    rpnQueue.add(popFromOperatorsStack())
                }
                if (!operatorsStack.isEmpty() && operatorsStack.get(operatorsStack.size() - 1).equals("(")) {
                    popFromOperatorsStack();
                }
            }
        })
    }

    private void initOperators() {
        operators = new HashMap<>();
        operators.put("*", Lexem.FIRST_ORDER_OPERATION);
        operators.put("/", Lexem.FIRST_ORDER_OPERATION);
        operators.put("-", Lexem.SECOND_ORDER_OPERATION);
        operators.put("+", Lexem.SECOND_ORDER_OPERATION);
        operators.put("(", Lexem.BRACKET);
        operators.put(")", Lexem.BRACKET);
    }

    private void pushToOperatorsStack(def value) {
        operatorsStack.add(value)
    }

    private String popFromOperatorsStack() {
        return operatorsStack.remove(operatorsStack.size() - 1);
    }

}
