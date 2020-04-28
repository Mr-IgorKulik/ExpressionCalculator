package com.mr_igor_kulik;

import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.LinkedList;

class ParserToPolish {

    private String expression;

    ParserToPolish (String expression) {
        this.expression = expression;
    }

    String parseToPolishExp() {
        if (expression == null) {
            throw new IllegalArgumentException("Expression can not be null.");
        }
        if (!isExpressionValid(expression)) {
            throw new IllegalArgumentException("Expression is invalid! " +
                    "Valid characters: digits, '+', '-', '*', '/', '(', ')', '^'.");
        }

        expression = skipSpaces(expression);
        char[] expCharArr = expression.toCharArray();
        StringBuilder resExp = new StringBuilder();
        Deque<Character> helpStack = new LinkedList<>();

        for (int i = 0; i < expCharArr.length; i++) {
            if (i < expCharArr.length - 1) {
                if ((Character.isDigit(expCharArr[i]) || expCharArr[i] == '.')
                        && ((Character.isDigit(expCharArr[i + 1]) || expCharArr[i + 1] == '.'))) {
                    resExp.append(expCharArr[i]);
                } else {
                    if (Character.isDigit(expCharArr[i])) {
                        resExp.append(expCharArr[i]);
                        resExp.append(' ');
                    }
                }
            } else {
                if (Character.isDigit(expCharArr[i])) {
                    resExp.append(expCharArr[i]);
                    resExp.append(' ');
                }
            }

            switch (expCharArr[i]) {
                case '(': {
                    helpStack.push('(');
                    break;
                }
                case '+': {
                    if (!helpStack.isEmpty()) {
                        while (helpStack.getFirst() == '*'
                                || helpStack.getFirst() == '/'
                                || helpStack.getFirst() == '-'
                                || helpStack.getFirst() == '^') {
                            resExp.append(helpStack.pop());
                            resExp.append(' ');
                        }
                    }
                    helpStack.push('+');
                    break;
                }
                case '-': {
                    if (!helpStack.isEmpty()) {
                        while (helpStack.getFirst() == '*'
                                || helpStack.getFirst() == '/'
                                || helpStack.getFirst() == '+'
                                || helpStack.getFirst() == '^') {
                            resExp.append(helpStack.pop());
                            resExp.append(' ');
                        }
                    }
                    helpStack.push('-');
                    break;
                }
                case '*': {
                    if (!helpStack.isEmpty()) {
                        while (helpStack.getFirst() == '/'
                                || helpStack.getFirst() == '^') {
                            resExp.append(helpStack.pop());
                            resExp.append(' ');
                        }
                    }
                    helpStack.push('*');
                    break;
                }
                case '/': {
                    if (!helpStack.isEmpty()) {
                        while (helpStack.getFirst() == '*'
                                || helpStack.getFirst() == '^') {
                            resExp.append(helpStack.pop());
                            resExp.append(' ');
                        }
                    }
                    helpStack.push('/');
                    break;
                }
                case '^': {
                    if (!helpStack.isEmpty()) {
                        while (helpStack.getFirst() == '^') {
                            resExp.append(helpStack.pop());
                            resExp.append(' ');
                        }
                    }
                    helpStack.push('^');
                    break;
                }
                case ')': {
                    if (!helpStack.isEmpty()) {
                        while (helpStack.getFirst() != '(') {
                            if (helpStack.size() != 0) {
                                resExp.append(helpStack.pop());
                                resExp.append(' ');
                            }
                            else throw new IllegalArgumentException("Brackets are not matched.");
                        }
                    }
                    helpStack.pop();
                    break;
                }
                }
        }

        while (!helpStack.isEmpty()) {
            resExp.append(helpStack.pop());
            resExp.append(' ');
        }

        return resExp.toString();
    }

    private static boolean isExpressionValid(@NotNull String expression) {
        char[] inputCharArr = expression.toCharArray();
        for (char element : inputCharArr) {
            if (!Character.isDigit(element)
                    & (element != '+')
                    & (element != '-')
                    & (element != '/')
                    & (element != '*')
                    & (element != ' ')
                    & (element != '.')
                    & (element != '(')
                    & (element != ')')
                    & (element != '^')) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    private static String skipSpaces (String expression) {
        StringBuilder exp = new StringBuilder(expression);
        StringBuilder resStr = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) != ' ') {
                resStr.append(exp.charAt(i));
            }
        }
        return resStr.toString();
    }
}
