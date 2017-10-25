package com.senpure.calculate;

/**
 * Created by 罗中正 on 2017/9/8.
 */
public class LetterAddOperator {
    public static void main(String[] args) {


        String express = "2a*5b";

        StringBuilder num = new StringBuilder();
        int length = express.length();
        char last = ' ';
        for (int i = 0; i < length; i++) {
            char now = express.charAt(i);


            last=now;
        }
    }
}
