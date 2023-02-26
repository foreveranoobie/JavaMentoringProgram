package com.epam.storozhuk

import com.epam.storozhuk.calculator.impl.CalculatorImpl

class Module5 {
    static void main(String[] args) {
        println("Enter the expression to calculate")
        def expression = System.console().readLine()
        def result = new CalculatorImpl().calculate(expression)
        println "The result is ${result}"
    }
}