package com.epam.storozhuk.number

import java.math.RoundingMode

class Number {
    private float number;

    Number(float number) {
        this.number = number
    }

    Number plus(Number number) {
        return new Number(new BigDecimal(this.number + number.getNumber()).setScale(2, RoundingMode.HALF_UP).floatValue())
    }

    Number minus(Number number) {
        return new Number(new BigDecimal(this.number - number.getNumber()).setScale(2, RoundingMode.HALF_UP).floatValue())
    }

    Number multiply(Number number) {
        return new Number(new BigDecimal(this.number * number.getNumber()).setScale(2, RoundingMode.HALF_UP).floatValue())
    }

    Number div(Number number) {
        return new Number(new BigDecimal(this.number / number.getNumber()).setScale(2, RoundingMode.HALF_UP).floatValue())
    }

    float getNumber(){
        return number
    }

    String toString() {
        return number.toString()
    }
}
