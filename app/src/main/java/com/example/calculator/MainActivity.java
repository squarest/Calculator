package com.example.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private String expression = "";
    private TextView expTextView;
    private TextView answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expTextView = (TextView) findViewById(R.id.input_text_view);
        answerTextView = (TextView) findViewById(R.id.answer_text_view);
//        String s = "3 + 4 * 2 / (1 - 5)^2";
//        String s2 = convertToOPN(s);
//        Log.d("TAG", s2);
//        Log.d("TAG", String.valueOf(calculate(s2)));
    }

    private String convertToOPN(String inputString) {
        Stack<Character> operators = new Stack<>();
        String outString = "";
        char[] symbols = inputString.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            if (Character.isDigit(symbols[i])) {
                outString += symbols[i];
                for (int j = i + 1; j < symbols.length; j++) {
                    if (Character.isDigit(symbols[j]) | symbols[j] == '.') {
                        outString += symbols[j];
                        i++;
                    } else {
                        break;
                    }
                }
                outString += ' ';
            } else switch (symbols[i]) {
                case '(': {
                    operators.push(symbols[i]);
                    break;
                }
                case '+':
                case '-':
                case '*':
                case '/':
                case '^': {
                    for (int j = 0; j < operators.size(); j++) {
                        if ((symbols[i] == '-' | symbols[i] == '/') & getPriority(symbols[i]) <= getPriority(operators.peek())
                                | getPriority(symbols[i]) < getPriority(operators.peek())) {
                            outString += String.valueOf(operators.pop()) + ' ';
                        } else {
                            break;
                        }
                    }
                    operators.push(symbols[i]);
                    break;
                }
                case ')': {
                    while (!operators.empty()) {
                        char operator = operators.pop();
                        if (operator != '(') {
                            outString += String.valueOf(operator) + ' ';
                        } else {
                            break;
                        }
                    }
                    break;
                }
            }
        }
        while (!operators.empty()) {
            outString += String.valueOf(operators.pop()) + ' ';
        }
        return outString;
    }

    private double calculate(String inputString) {
        inputString = convertToOPN(inputString);
        String[] operands = inputString.split(" ");
        Stack<Double> stack = new Stack<>();
        for (String operand : operands) {
            if (isDouble(operand)) {
                stack.push(Double.parseDouble(operand));
            } else switch (operand) {
                case "+": {
                    stack.push(stack.pop() + stack.pop());
                    break;
                }
                case "-": {
                    double temp = stack.pop();
                    stack.push(stack.pop() - temp);
                    break;
                }
                case "*": {
                    stack.push(stack.pop() * stack.pop());
                    break;
                }
                case "/": {
                    double temp = stack.pop();
                    if (temp != 0) {
                        stack.push(stack.pop() / temp);
                    }
                    break;
                }
                case "^": {
                    double temp = stack.pop();
                    stack.push(Math.pow(stack.pop(), temp));
                    break;
                }
            }
        }
        return stack.pop();
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int getPriority(char operator) {
        switch (operator) {
            case '(':
            case ')':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 4;
        }
    }

    public void onClick(View v) {
        Button b = (Button) v;
        switch (b.getText().toString()) {
            case "DEL": {
                expression = expTextView.getText().toString();
                expTextView.setText(expression.substring(0, expression.length() - 1));
                break;
            }
            case "C": {
                expTextView.setText("");
                break;
            }
            case "=": {
                expression = expTextView.getText().toString();
                if (!expression.isEmpty()) {
                    answerTextView.setText("= " + calculate(expression));
                }
                break;
            }
            default: {
                if (Character.isDigit(b.getText().toString().charAt(0))) {
                    expTextView.setText(expTextView.getText().toString() + b.getText().toString());
                } else {
                    expTextView.setText(expTextView.getText().toString() + " " + b.getText().toString() + " ");
                }

                break;
            }
        }
    }
}
