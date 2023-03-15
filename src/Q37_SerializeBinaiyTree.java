import com.sun.source.tree.BinaryTree;
import utils.BinaryTreeNode;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * 二叉树的序列化和反序列化
 *
 * 反序列化 未检查输入字符串是否合法 能构建出二叉树
 */
public class Q37_SerializeBinaiyTree {
    /**
     * M1 深度优先搜索	O(n),O(n)
     * 先序遍历 空占位
     * 先序遍历 遇到空结点用特殊符号'$'代表
     *      空树返回 "$"
     * 先序遍历替换为层序遍历也可
     */
    private static final String NULL_NODE = "$";
    /** ---------- 递归法 ---------- */
    /** 序列化 */
    public static String serialize(BinaryTreeNode root) {
        StringBuilder result = new StringBuilder();
        result = serializeDfs(root, result);
        return result.deleteCharAt(result.length() - 1).toString();     // 删除尾多余的","  删尾不删头得原因是StringBuilder的底层实现是数组,删尾效率高 删头需要所有元素前移
    }
    private static StringBuilder serializeDfs(BinaryTreeNode node, StringBuilder result) {
        // 基例
        if (node == null) {
            result.append(NULL_NODE).append(",");
        } else {
            result.append(node.val);
            result.append(",");
            serializeDfs(node.left, result);
            serializeDfs(node.right, result);
        }
        return result;
    }

    /** 反序列化 */
    public static BinaryTreeNode deserialize(String data) {
        List<String> dataList = new LinkedList<String>(Arrays.asList(data.split(",")));     // 频繁删头选用LinkedList
        return deserializeDfs(dataList);
    }
    private static BinaryTreeNode deserializeDfs(List<String> dataList) {
        String valString = dataList.get(0);
        // 基例
        if (NULL_NODE.equals(valString)) {
            dataList.remove(0);
            return null;
        }
        BinaryTreeNode node = new BinaryTreeNode(Integer.parseInt(valString));
        dataList.remove(0);
        node.left = deserializeDfs(dataList);
        node.right = deserializeDfs(dataList);
        return node;
    }

    /** ---------- 循环法 ---------- */
    /** ----- 循环法: 先序遍历 ----- */
    public String serializeLoop(BinaryTreeNode root) {
        StringBuilder res = new StringBuilder();
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();   // 处理完的上一层结点
        stack.push(root);
        BinaryTreeNode node;
        while(!stack.isEmpty()) {
            node = stack.pop();
            if(node == null) {
                res.append(NULL_NODE).append(",");
            } else {
                res.append(node.val).append(",");
                // 压栈 先右后左
                stack.add(node.right);
                stack.add(node.left);
            }
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }
    /** 反列化 */
    public BinaryTreeNode deserializeLoop(String data) {
        List<String> dataList = new LinkedList<String>(Arrays.asList(data.split(",")));
        // 处理根结点
        if (NULL_NODE.equals(dataList.get(0))) {  // 空树
            return null;
        }
        BinaryTreeNode root = new BinaryTreeNode(Integer.parseInt(dataList.get(0)));
        dataList.remove(0);

        // 结点状态: 记录栈中结点的处理状态, 以帮助判断序列中结点是栈中结点的左子还是右子
        final boolean LEFT = false;     // 左子未处理 (此结点)下个插入位置是左子
        final boolean Right = true;     // 右子未处理(处理完左子) (此结点)下个插入位置是右子
        // 保存未处理完成结点
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        Stack<Boolean> stackNodeState = new Stack<Boolean>();   // 保存栈中对应结点的处理状态
        stack.push(root);
        stackNodeState.push(LEFT);

        // 为上层结点新建当前层结点: 左子右子
        BinaryTreeNode node;    // 栈顶结点
        Boolean nodeState;      // 栈顶结点状态
        String valStirng;       // 序列当前结点值的字符串
        while(!stack.isEmpty()) {
            node = stack.pop();
            nodeState = stackNodeState.pop();
            valStirng = dataList.get(0);

            // 根据结点状态对当前结点 根据序列当前结点值 新建左子或右子
            if (nodeState == LEFT) {  // 左子未处理
                // 压栈顺序: 先根后子
                // 更新结点状态 处理完左子 Right
                stack.push(node);
                stackNodeState.push(Right);      // stackNodeState.push(++nodeState);
                if (!NULL_NODE.equals(valStirng)) {  // 序列当前结点值空就跳过
                    node.left = new BinaryTreeNode(Integer.parseInt(valStirng));
                    stack.add(node.left);
                    stackNodeState.push(LEFT);  // 新压栈结点的状态一定是左子未处理
                }
                dataList.remove(0);
            } else {  // 右子未处理
                // 压栈顺序: 当前结点处理完不需再压 只需要压子
                if (!NULL_NODE.equals(valStirng)) {  // 序列当前结点值空就跳过
                    node.right = new BinaryTreeNode(Integer.parseInt(valStirng));
                    stack.add(node.right);
                    stackNodeState.push(LEFT);  // 新压栈结点的状态一定是左子未处理
                }
                dataList.remove(0);
            }
        }
        return root;
    }

    /** ----- 循环法: 层序遍历 ----- */
    /** 序列化 */
    public String serializeLoopInorder(BinaryTreeNode root) {
        StringBuilder res = new StringBuilder();
        Queue<BinaryTreeNode> queue = new LinkedList<>();   // 处理完的上一层结点
        queue.add(root);
        BinaryTreeNode node;
        while(!queue.isEmpty()) {
            node = queue.poll();
            if(node == null) {
                res.append(NULL_NODE).append(",");
            } else {
                res.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

    /** 反列化 */
    public BinaryTreeNode deserializeLoopInorder(String data) {
        List<String> dataList = new LinkedList<String>(Arrays.asList(data.split(",")));
        // 处理根结点
        if (NULL_NODE.equals(dataList.get(0))) {  // 空树
            return null;
        }
        BinaryTreeNode root = new BinaryTreeNode(Integer.parseInt(dataList.get(0)));
        dataList.remove(0);
        // 保存已经建好的上层结点
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);

        // 为上层结点新建当前层结点: 左子右子
        BinaryTreeNode node;
        while(!queue.isEmpty()) {
            node = queue.poll();
            // 左子
            if(!NULL_NODE.equals(dataList.get(0))) {  // 空就跳过
                node.left = new BinaryTreeNode(Integer.parseInt(dataList.get(0)));
                queue.add(node.left);
            }
            dataList.remove(0);
            // 右子
            if(!NULL_NODE.equals(dataList.get(0))) {  // 空就跳过
                node.right = new BinaryTreeNode(Integer.parseInt(dataList.get(0)));
                queue.add(node.left);
            }
            dataList.remove(0);
        }
        return root;
    }



    /**
     * M2 括号编码+递归下降解码	O(n),O(n)
     * 序列化 编码
     *      空树	`X`
     *      非空树	`(左子树)根结点值(右子树)`
     * 反序列化 解码
     *      巴科斯范式BNF:
     *      ```
     *      T -> (T)value(T) | X
     *      ```
     *      `|` 或, 左边是递归定义, 右边是递归终止边界条件.
     * 	        `T` 树编码, `X` 空树编码
     * 解码时 序列首个字符是`X`或`(`代表空树或递归, 是无二义性的LL(1)型文法(见编译原理)
     */
    /** 静态字符 特有字符 */
    private static final char NULL_TREE = 'X';
    private static final char LEFT_PARENTHESIS  = '(';
    private static final char RIGHT_PARENTHESIS = ')';
    /** 编码 */
    public static String encode(BinaryTreeNode root) {
        return encodeRecursion(root, new StringBuilder()).toString();
    }
    private static StringBuilder encodeRecursion(BinaryTreeNode root, StringBuilder result) {
        // T -> (T)value(T) | X
        // X
        if (root == null) {
            return result.append(NULL_TREE);
        }
        // (T)value(T)
        result.append(LEFT_PARENTHESIS);
        encodeRecursion(root.left, result);
        result.append(RIGHT_PARENTHESIS);
        result.append(root.val);
        result.append(LEFT_PARENTHESIS);
        encodeRecursion(root.right, result);
        result.append(RIGHT_PARENTHESIS);
        return result;
    }
    /** 解码 */
    public static BinaryTreeNode decode(String data) {
        // ptr记录字符串遍历当前下标
        int[] ptr = {0};
        return parse(data, ptr);
    }
    private static BinaryTreeNode parse(String data, int[] ptr) {
        // X
        if (data.charAt(ptr[0]) == NULL_TREE) {
            ++ptr[0];
            return null;
        }
        // (T)value(T)
        BinaryTreeNode cur = new BinaryTreeNode(0);
        cur.left = parseSubtree(data, ptr);
        cur.val = parseInt(data, ptr);
        cur.right = parseSubtree(data, ptr);
        return cur;
    }
    private static BinaryTreeNode parseSubtree(String data, int[] ptr) {
        ++ptr[0]; // 跳过左括号
        BinaryTreeNode subtree = parse(data, ptr);
        ++ptr[0]; // 跳过右括号
        return subtree;
    }
    private static int parseInt(String data, int[] ptr) {
        int res = 0;
        int sgn = 1;    // 符号位: 负号
        // 符号位
        if (!Character.isDigit(data.charAt(ptr[0]))) {
            sgn = -1;
            ++ptr[0];
        }
        // 数字
        // 每位叠加 而不是先取出String再Integer.parseInt()
        while (Character.isDigit(data.charAt(ptr[0]))) {
            res = res * 10 + (data.charAt(ptr[0]++) - '0'); // 用char的差算出数字值
        }
        return res * sgn;
    }

}

