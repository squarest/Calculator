package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = "(1 + 2) * 4 + 3 ";
        Stack<Character> operators = new Stack<>();
        String outString = "";
        char[] symbols = s.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            if (Character.isDigit(symbols[i])) {
                outString += symbols[i];
                for (int j = i + 1; j < symbols.length; j++) {
                    if (Character.isDigit(symbols[j])) {
                        outString += symbols[j];
                    } else break;
                }
                outString += ' ';
            } else if (symbols[i] == '+' | symbols[i] == '-' | symbols[i] == '(' | symbols[i] == '*') {
                operators.push(symbols[i]);
            } else if (symbols[i] == ')') {
                for (int j=0; j<operators.size(); j++) {
                    if (!operators.empty()) {
                        char operator=operators.pop();
                        if (operator != '(') {
                            outString += String.valueOf(operator) + ' ';
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        for (char operator : operators) {
            outString += String.valueOf(operator) + ' ';
        }
        Log.d("TAG", outString);
    }
}
