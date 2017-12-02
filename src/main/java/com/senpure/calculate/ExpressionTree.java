package com.senpure.calculate;


import com.senpure.base.util.Assert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import java.util.*;

/**
 * 先将输入表达式拆分为一个一个的节点 如数字5 加号 等
 * 然后依次找到最后的算术节点为树的算术节点，构成表达式树，然后遍历表达式树，算出结果
 * Created by 罗中正 on 2017/6/23.
 */
public class ExpressionTree {

    private static char[] SUPPORT_OPERATOR = {'+', '-', '*', '/'};
    private static Logger logger = LoggerFactory.getLogger(ExpressionTree.class);


    private static AnsiColor[] colors = {AnsiColor.MAGENTA, AnsiColor.RED, AnsiColor.YELLOW, AnsiColor.CYAN, AnsiColor.BLUE};
    private Node root;
    private Map<String, Number> args = new HashMap<>();
    //字母是否赋值了。
    private boolean updateLetter;
    private boolean notAllowUpdate = false;

    private ExpressionTree() {
    }

    public void updateArgs(Map<String, Number> args) {
        if (notAllowUpdate) {
            Assert.error("该表达式不允许更新参数,请使用无参数的构造函数可以更新参数");
        }
        updateLetter = true;
        this.args.clear();

        this.args.putAll(args);

    }

    public double caluate() {


        if (updateLetter) {
            inOrderUpdateLetter(root);
            updateLetter = false;
        }

        return calculate(root);
    }

    public void inOrderUpdateLetter(Node node) {
        if (node != null) {
            Number data = args.get(node.metaData);
            if (data != null) {
                node.data = data.toString();
            }
            inOrderUpdateLetter(node.left);
            inOrderUpdateLetter(node.right);
        }

    }

    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString();
    }

    private void inOrder(Node node, StringBuilder sb) {


        if (node != null) {
            inOrder(node.left, sb);
            sb.append(node.toString());
            inOrder(node.right, sb);
        }

    }

    public String preOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString();
    }

    private void preOrder(Node node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.toString());
            preOrder(node.left, sb);
            preOrder(node.right, sb);
        }

    }

    public String postOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(root, sb);
        return sb.toString();
    }

    private void postOrder(Node node, StringBuilder sb) {


        if (node != null) {
            postOrder(node.left, sb);
            postOrder(node.right, sb);
            sb.append(node.toString());
        }
    }

    private int getDeep(Node node, int parentDeep) {
        if (node == null) {
            return parentDeep;
        }
        int left = getDeep(node.left, parentDeep + 1);
        int right = getDeep(node.right, parentDeep + 1);
        return left > right ? left : right;
    }

    public int getSpace(Node node, int space, boolean metaData) {
        if (node == null) {

            return space;
        }
        int l = metaData ? node.data.length() : node.data.length();
        int t = l > space ? l : space;
        int left = getSpace(node.left, t, metaData);
        int right = getSpace(node.right, t, metaData);
        return left > right ? left : right;
    }

    public String treeStr() {

        return treeStr(true, true);
    }

    public String treeStr(boolean color) {

        return treeStr(color, true);
    }

    public String treeStr(boolean color, boolean metaData) {
        StringBuilder sb = new StringBuilder();
        int deep = getDeep(root, 0);
        int bank = getSpace(root, 0, metaData);
        logger.trace("deep is {} bank is {}", deep, bank);

        String emptyStr = StringUtils.center(" ", bank);
        if (color) {
            emptyStr = AnsiOutput.toString(AnsiColor.BRIGHT_WHITE, emptyStr);
        }

        logger.trace("emptyStr is [{}]", emptyStr);
        List<List<LevelNode>> outlevelNodes = new ArrayList<>();

        List<LevelNode> currentLevelNodes = new ArrayList<>();
        LevelNode levelNode = levelNode(root, 1, 0);

        currentLevelNodes.add(levelNode);
        do {
            outlevelNodes.add(currentLevelNodes);
            currentLevelNodes = nextLevelNode(currentLevelNodes);
        }
        while (currentLevelNodes.size() > 0);

        int level = 1;


        for (List<LevelNode> levelNodes : outlevelNodes) {
            StringBuilder builder = new StringBuilder();
            builder.append("第{}层 index is ");
            int space = (int) (Math.pow(2, (deep - level)) - 1);

            for (int i = 0; i < space; i++) {

                sb.append(emptyStr);
            }


            int lastIndex = 0;
            AnsiColor withColor = colors[level % colors.length];
            int sort = 0;
            for (LevelNode ln : levelNodes) {

                builder.append(ln.getIndex()).append(",");

                int interval = ln.getIndex() - lastIndex;
                if (sort == 0 && ln.getIndex() != 0) {
                    sb.append(emptyStr);
                }
                lastIndex = ln.getIndex();
                for (int i = 0; i < interval; i++) {

                    space = (int) (Math.pow(2, (deep - level + 1)) - 1);

                    for (int j = 0; j < space; j++) {

                        sb.append(emptyStr);
                    }
                }
                for (int i = 1; i < interval; i++) {
                    sb.append(emptyStr);
                }
                String e = StringUtils.center(metaData ? ln.getMetaData() : ln.getData(), bank);
                if (color) {
                    e = AnsiOutput.toString(withColor, e);
                }

                sb.append(e);
                sort++;
            }
            //  logger.trace(builder.toString(), level);
            level++;
            sb.append("\n");

        }

        return sb.toString();

    }

    private List<LevelNode> nextLevelNode(List<LevelNode> nodes) {
        List<LevelNode> nextLevelNodes = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            LevelNode temp = nodes.get(i);
            Node node = temp.getNode();
            if (node.left != null) {
                nextLevelNodes.add(levelNode(node.left, temp.getLevel() + 1, temp.getIndex() * 2));
            }
            if (node.right != null) {
                nextLevelNodes.add(levelNode(node.right, temp.getLevel() + 1, temp.getIndex() * 2 + 1));
            }
        }

        return nextLevelNodes;
    }

    private LevelNode levelNode(Node node, int level, int index) {
        LevelNode levelNode = new LevelNode();
        levelNode.setIndex(index);
        levelNode.setLevel(level);
        levelNode.setData(node.data);
        levelNode.setMetaData(node.metaData);
        levelNode.setNode(node);
        return levelNode;

    }


    public static boolean isSupportOperator(char operator) {
        for (char c : SUPPORT_OPERATOR) {
            if (c == operator) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNum(char value) {

        return value >= 48 && value <= 57;
    }

    public static boolean isLetter(char c) {

        return c > 96 && c < 123 || (c > 64 && c < 91);
    }

    private static int findOperator(List<Node> nodes, int start, int end) {

        if (logger.isTraceEnabled()) {

            StringBuilder sb = new StringBuilder();
            for (int i = start; i <= end; i++) {

                sb.append(nodes.get(i));
            }
            logger.trace("寻找操作符start {}  end {} {}", start, end, sb);
        }

        int index = -1;

        int bracket = 0;
        boolean noAddOrSubtract = true;
        for (int i = start; i <= end; i++) {
            String data = nodes.get(i).data;

            if (data.equals("(")) {
                bracket++;
            } else if (data.equals(")")) {
                bracket--;
            } else if (bracket == 0 && (data.equals("-") || data.equals("+"))) {

                index = i;

                noAddOrSubtract = false;
                // logger.trace("找到{} index {}", data, index);

            } else if (bracket == 0 && noAddOrSubtract && (data.equals("*") || data.equals("/"))) {
                index = i;
                //  logger.trace("找到{} index {}", data, index);
            }
        }
        if (bracket != 0) {
            Assert.error("表达式错误括号不成对" + nodes);
        }
        // logger.trace("返回 index {}", index);
        return index;
    }

    private static Node createTree(List<Node> nodes, int start, int end) {
        while (nodes.get(start).data.equals("(") && nodes.get(end).data.equals(")")) {
            start++;
            end--;
        }
        int index = findOperator(nodes, start, end);
        Node node = null;
        if (index == -1) {
            node = nodes.get(start);

            // logger.trace("node is {}", node.data);
        } else {
            node = nodes.get(index);

            //  logger.trace("node is {}", node.data);
            node.left = createTree(nodes, start, index - 1);
            node.right = createTree(nodes, index + 1, end);

        }
        return node;
    }

    public static String preProcess(String express) {
        logger.trace("express {}", express);
        express = express.replace(" ", "");
        logger.trace("express preprocessed {}", express);
        int length = express.length();
        char last = ' ';
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char now = express.charAt(i);
            if (isLetter(now)) {
                if (isNum(last) || isLetter(last)) {
                    num.append("*");
                }
            }
            num.append(now);
            last = now;
        }
        return num.toString();
    }

    private static boolean preProcess(String express, List<Node> nodes) {
        return preProcess(express, nodes, true);
    }

    private static boolean preProcess(String express, List<Node> nodes, boolean check) {

        boolean hasLetter = false;
        if (check) {
            express = preProcess(express);
        }

        int length = express.length();
        char last = ' ';
        StringBuilder num = new StringBuilder();
        logger.trace("express preprocessed {}", express);

        for (int i = 0; i < length; i++) {
            char now = express.charAt(i);
            if (isNum(now)) {
                if (isLetter(last)) {
                    Assert.error("表达式错误 [" + last + now + "]");
                }
                num.append(now);
            } else if (now == '.') {
                num.append(now);
            } else if (isLetter(now)) {
                hasLetter = true;
                if (num.length() > 0) {
                    nodes.add(new Node(num.toString()));
                    num.delete(0, num.length());
                }
                nodes.add(new Node(now + ""));

            } else {
                //开头不能说运行符
                if (i == 0 && (now != '(')) {
                    Assert.error("运算位置错误符错误 [" + last + now + "]");
                }
                //最后不能是运算符
                if (i == length - 1 && (now != ')')) {
                    Assert.error("运算位置错误符错误 [" + last + now + "]");
                }

                boolean readOverNum = true;

                if (now == '-') { // if (last == ')' || isNum(last))//减号
                    // if (isNum(last))//减号
                    if (last == ')' || isNum(last) || isLetter(last))//减号
                    {

                    } else {//负数
                        num.append(now);
                        readOverNum = false;
                    }
                }
                //除括号外运算符前面必定为数字或字母
                else if (i != 0 && now != '(' && now != ')' && !isNum(last) && !isLetter(last) && last != '(' && last != ')') {
                    Assert.error("运算位置错误符错误 [" + last + now + "]");
                }
                if (readOverNum) {

                    if (num.length() > 0) {
                        nodes.add(new Node(num.toString()));
                        num.delete(0, num.length());
                    }

                    OPERATOR operator = OPERATOR.getOperator(now);
                    if (operator == null) {
                        Assert.error("不支持的运算符 [" + now + "]");
                    }
                    nodes.add(new Node(operator));

                }

            }
            last = now;
        }
        if (num.length() > 0) {
            //   Element element=   new  Element(Double.parseDouble(num.toString()));
            nodes.add(new Node(num.toString()));
        }
        return hasLetter;
    }


    private static double calculate(Node node) {
        if (node.left == null) {
            return Double.parseDouble(node.data);
        }
        if (node.left.isNum()) {
            double a = Double.parseDouble(node.left.data);
            if (node.right.isNum()) {
                double b = Double.parseDouble(node.right.data);
                return calculate(a, node, b);
            } else {

                return calculate(a, node, calculate(node.right));


            }
        } else {
            if (node.right.isNum()) {

                double b = Double.parseDouble(node.right.data);
                return calculate(calculate(node.left), node, b);
            } else {
                return calculate(calculate(node.left), node, calculate(node.right));
            }

        }

    }

    private static double calculate(double a, Node operator, double b) {
        // double a = Double.parseDouble(first.data);
        //  double b = Double.parseDouble(second.data);

        double c = 0;
        switch (operator.operator) {
            case ADD:
                c = a + b;
                logger.trace("{} {} {} = {}", a, operator.operator.getOpeator(), b, c);
                // return new Node(c + "");
                return c;

            case SUBTRACT:
                c = a - b;
                logger.trace("{} {} {} = {}", a, operator.operator.getOpeator(), b, c);
                // return new Node(c + "");
                return c;
            case MULTIPLY:
                c = a * b;
                logger.trace("{} {} {} = {}", a, operator.operator.getOpeator(), b, c);
                //return new Node(c + "");
                return c;
            case DIVIDE:
                c = a / b;

                logger.trace("{} {} {} = {}", a, operator.operator.getOpeator(), b, c);
                //return new Node(c + "");
                return c;

        }
        return c;
    }


    private static void inOrder(Node node) {

        if (node != null) {

            inOrder(node.left);
            System.out.print(node.data);
            inOrder(node.right);

        }
    }

    private static void preOrder(Node node) {

        if (node != null) {
            System.out.print(node.data);
            preOrder(node.left);

            preOrder(node.right);
        }
    }


    private static void postOrder(Node node) {

        if (node != null) {
            postOrder(node.left);

            postOrder(node.right);
            System.out.print(node.data);
        }

    }

    /**
     * 支持动态更新参数列表
     *
     * @param express
     * @return
     */
    public static ExpressionTree build(String express) {

        return build(express, null);
    }

    /**
     * 不支持动态更新参数列表
     *
     * @param express
     * @param args
     * @return
     */
    public static ExpressionTree build(String express, Map<String, Number> args) {
        ExpressionTree expressionTree = new ExpressionTree();
        List<Node> nodes = new ArrayList<>();
        boolean hasLetter;
        if (args != null) {
            expressionTree.notAllowUpdate = true;
            express = preProcess(express);
            Iterator<Map.Entry<String, Number>> iterator = args.entrySet().iterator();
            while (iterator.hasNext()) {

                Map.Entry<String, Number> entry = iterator.next();
                express = express.replace(entry.getKey(), entry.getValue().toString());
            }

            hasLetter = preProcess(express, nodes, false);
        } else {
            hasLetter = preProcess(express, nodes);
        }

        logger.trace("nodes size {} {}", nodes.size(), nodes.toString());

        Node tree = createTree(nodes, 0, nodes.size() - 1);

        expressionTree.root = tree;
        expressionTree.updateLetter = hasLetter;


        return expressionTree;

    }


    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        //String express = "3 +7*4";
        // String express = "3 + 4 * (50 - 6)";
        //String express = "(3 +5) -6";
        String express = "100a*50+100 /2+3 0+-5* 61";
        //String express = "3*(-3)";
        express = "2a*50+1c /2+32+-5* 61";


        Map<String, Number> content = new HashMap<>();
        content.put("a", 45);
        content.put("b", 45);
        content.put("c", 45);
        content.put("d", 45);
        ExpressionTree tree = build(express, content);
       // tree.updateArgs(content);
        logger.trace("caluate {}", tree.caluate());

        logger.trace("表达式二叉树\n{}", tree.treeStr());
        logger.trace("先根遍历{}", tree.preOrder());
        logger.trace("中根遍历{}", tree.inOrder());
        logger.trace("后根遍历{}", tree.postOrder());
    }
}
