package com.mr_igor_kulik;

import java.util.Deque;
import java.util.LinkedList;

public class Calculator {

    public static Double calculate (String expression) {

        ParserToPolish parser = new ParserToPolish(expression);
        String exp = parser.parseToPolishExp();
        Deque<Double> resStack = new LinkedList<>();
        char[] inputCharArr = exp.toCharArray();
        StringBuilder inputStr = new StringBuilder(exp);

            for (int i = 0; i < inputCharArr.length; i++) {
            char element = inputCharArr[i];

            if(element == ' ') {
                if(inputStr.length() != 0) {
                    inputStr.deleteCharAt(0);
                }
            } else if (Character.isDigit(element)){
                int count = 0;
                StringBuilder tmpStr = new StringBuilder("");
                while (Character.isDigit(inputStr.toString().charAt(count))
                        | inputStr.toString().charAt(count) == '.' ) {
                    tmpStr.append(inputStr.toString().charAt(count));
                    count++;
                }
                Double doubleEl = Double.parseDouble(tmpStr.toString());
                resStack.push(doubleEl);
                for(int j = 0; j < count - 1; j++) {
                    i++;
                }
                if (count > 0) {
                    while (count > 0) {
                        inputStr.deleteCharAt(0);
                        count--;
                    }
                } else inputStr.deleteCharAt(0);
            } else switch (element) {
                case '+': {
                    Double firstOperand = (Double) resStack.pop();
                    Double secondOperand = (Double) resStack.pop();
                    resStack.push(firstOperand + secondOperand);
                    inputStr.deleteCharAt(0);
                    break;
                }
                case '*': {
                    Double firstOperand = (Double) resStack.pop();
                    Double secondOperand = (Double) resStack.pop();
                    resStack.push(firstOperand * secondOperand);
                    inputStr.deleteCharAt(0);
                    break;
                }
                case '-': {
                    Double firstOperand = (Double) resStack.pop();
                    Double secondOperand = (Double) resStack.pop();
                    resStack.push(secondOperand - firstOperand);
                    inputStr.deleteCharAt(0);
                    break;
                }
                case '/': {
                    try {
                        Double firstOperand = (Double) resStack.pop();
                        Double secondOperand = (Double) resStack.pop();
                        resStack.push(secondOperand / firstOperand);
                        inputStr.deleteCharAt(0);
                    } catch (ArithmeticException exc) {
                        throw new ArithmeticException();
                    }
                    break;
                }
                case '^': {
                    Double firstOperand = (Double) resStack.pop();
                    Double secondOperand = (Double) resStack.pop();
                    resStack.push(Math.pow(secondOperand, firstOperand));
                    inputStr.deleteCharAt(0);
                    break;
                }
            }
        }
            return (Double) resStack.pop();
    }
}
