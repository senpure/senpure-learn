package com.senpure.calculate;

/**
 * Created by 罗中正 on 2017/6/27.
 */
public enum OPERATOR {
    ADD('+', 1),
    SUBTRACT('-', 1),
    MULTIPLY('*', 2),
    DIVIDE('/', 2),
    BRACKET_LEFT('(', 100),
    BRACKET_RIGHT(')', 100);
    private char opeator;
    private int level;

    OPERATOR(char opeator, int level) {
        this.opeator = opeator;
        this.level = level;
    }

    public char getOpeator()

    {
        return opeator;
    }

    public int getLevel() {
        return level;
    }

    public static OPERATOR getOperator(char opeator) {
        OPERATOR operators[] = OPERATOR.values();
        for (OPERATOR o : operators) {
            if (o.opeator == opeator) {
                return o;
            }
        }
        return null;
    }
}
