package com.epam.storozhuk.number

import java.math.RoundingMode

class Number {
    private BigInteger number;

    Number(BigInteger number) {
        this.number = number
    }

    Number plus(Number number) {
        return new Number(this.number.add(number.getNumber()))
    }

    Number minus(Number number) {
        return new Number(this.number.subtract(number.getNumber()))
    }

    Number multiply(Number number) {
        return new Number(this.number.multiply(number.getNumber()))
    }

    Number div(Number number) {
        return new Number(this.number.divide(number.getNumber()))
    }

    BigInteger getNumber() {
        return number
    }

    String toString() {
        return number.toString()
    }
}
