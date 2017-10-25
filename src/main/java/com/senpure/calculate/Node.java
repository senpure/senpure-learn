package com.senpure.calculate;

/**
 * Created by 罗中正 on 2017/6/27.
 */
public class Node {
    String data;
    String metaData;
    OPERATOR operator;
    Node left;
    Node right;


    public Node(String metaData ) {
        this.data = metaData ;
        this.metaData = metaData ;

    }

    public Node(OPERATOR operator) {
        this.data = operator.getOpeator() + "";
        this.operator = operator;
        this.metaData=data;

    }

    public Node(OPERATOR operator, Node left, Node right) {
        this.data = operator.getOpeator() + "";
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    private void setDeep(int deep) {

    }

    public boolean isNum() {
        return operator == null;

    }


    @Override
    public String toString() {

        if (isNum()) {
            return "[" + metaData + "]";
        }
        return metaData;
    }
}
