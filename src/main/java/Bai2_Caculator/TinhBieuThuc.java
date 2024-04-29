package Bai2_Caculator;

import java.util.*;

public class TinhBieuThuc {
    private static final Map<Character, Integer> OPERATOR_PRECEDENCE = Map.of(
            '+', 1,
            '-', 1,
            '*', 2,
            '/', 2,
            '^', 3
    );

    public static double eval(String expression) {
        Queue<Object> outputQueue = new LinkedList<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);

            if (Character.isDigit(token)) {
                StringBuilder number = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    number.append(expression.charAt(i));
                    i++;
                }
                i--;
                outputQueue.add(Double.parseDouble(number.toString()));
            } else if (token == '(') {
                operatorStack.push(token);
            } else if (token == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.pop(); // Pop the '('
            } else if (OPERATOR_PRECEDENCE.containsKey(token)) {
                while (!operatorStack.isEmpty() && OPERATOR_PRECEDENCE.get(token) <= OPERATOR_PRECEDENCE.getOrDefault(operatorStack.peek(), 0)) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        Deque<Double> operandStack = new ArrayDeque<>();
        while (!outputQueue.isEmpty()) {
            Object token = outputQueue.poll();
            if (token instanceof Double) {
                operandStack.push((Double) token);
            } else {
                char operator = (char) token;
                double operand2 = operandStack.pop();
                double operand1 = operandStack.pop();
                switch (operator) {
                    case '+':
                        operandStack.push(operand1 + operand2);
                        break;
                    case '-':
                        operandStack.push(operand1 - operand2);
                        break;
                    case '*':
                        operandStack.push(operand1 * operand2);
                        break;
                    case '/':
                        operandStack.push(operand1 / operand2);
                        break;
                    case '^':
                        operandStack.push(Math.pow(operand1, operand2));
                        break;
                }
            }
        }

        return operandStack.pop();
    }
}