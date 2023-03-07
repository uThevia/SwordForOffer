package utils;

import java.util.*;
import java.util.Stack;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 二叉树
 */
public class BinaryTreeNode {
    public int val;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    /**
     * 构造
     */
    public BinaryTreeNode() {}
    public BinaryTreeNode(int val) { this.val = val; }
    public BinaryTreeNode(int val, BinaryTreeNode left, BinaryTreeNode right) { this.val = val; this.left = left; this.right = right;}


    /** ------------------------------------------- 遍历 -------------------------------------------------*/
    /**
     * 先序遍历
     */
    /** 先序遍历: 递归 */
    public static List<Integer> preOrderRecursion(BinaryTreeNode root){
        List<Integer> result = new ArrayList<Integer>();
        if(root == null)
            return result;
        preOrderRecursionStep(root, result);
        return result;
    }
    private static void preOrderRecursionStep(BinaryTreeNode root, List<Integer> result){
        if(root == null)
            return;
        result.add(root.val);
        preOrderRecursionStep(root.left, result);
        preOrderRecursionStep(root.right, result);
    }
    /** 先序遍历: 非递归 */
    public static List<Integer> preOrder(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null)
            return result;

        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();  // 暂存结点的栈
        BinaryTreeNode node = root; // 从根结点开始
        stack.push(node);           // 压栈
        while (!stack.isEmpty()) {              // 遍历完成时栈空
            node = stack.pop();
            result.add(node.val);
            // 先右后左 出栈顺序位为"根右左"
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
        return result;
    }
    /** 先序遍历: 非递归 复杂方法*/
    public static List<Integer> preOrderOld(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null)
            return result;

        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();      // 暂存结点的栈
        BinaryTreeNode node = root;     // 新建游标结点指向根结点
        // 先左后右
        while (!stack.isEmpty() || node != null) {  // 遍历完成时左右子树为空且栈空
            // 左子树 直到底部 游标为空
            while (node != null) {
                result.add(node.val);   // * 遍历该结点
                stack.push(node);       // 暂存结点以之后找到该结点右子树
                node = node.left;
            }
            // 右子树
            if (!stack.isEmpty()) {     // 如果栈空就说明遍历结束
                // 弹出栈顶元素并将游标指向右子树
                node = stack.pop();
                node = node.right;
            }
        }
        return  result;
    }

    /**
     * 中序遍历
     */
    /** 中序遍历: 递归 */
    public static List<Integer> inOrderRecursion(BinaryTreeNode root){
        List<Integer> result = new ArrayList<Integer>();
        if(root == null)
            return result;
        inOrderRecursionStep(root, result);
        return result;
    }
    private static void inOrderRecursionStep(BinaryTreeNode root, List<Integer> result){
        if(root == null)
            return;
        inOrderRecursionStep(root.left, result);
        result.add(root.val);
        inOrderRecursionStep(root.right, result);
    }
    /** 中序遍历: 非递归 */
    public static List<Integer> inOrder(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null)
            return result;
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        BinaryTreeNode node = root;
        while (!stack.isEmpty() || node != null){    // 遍历完成时左右子树为空且栈空
            // 左子树 直到底部 游标为空
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {     // 如果栈空就说明遍历结束
                // 弹出栈顶元素遍历并将游标指向右子树
                node = stack.pop();
                result.add(node.val);   // * 遍历该结点
                node = node.right;
            }
        }
        return result;
    }
    
    /**
     * 后序遍历
     */
    /** 后序遍历: 递归 */
    public static List<Integer> postOrderRecursion(BinaryTreeNode root){
        List<Integer> result = new ArrayList<Integer>();
        if(root == null)
            return result;
        postOrderRecursionStep(root, result);
        return result;
    }
    private static void postOrderRecursionStep(BinaryTreeNode root, List<Integer> result){
        if(root == null)
            return;
        postOrderRecursionStep(root.left, result);
        postOrderRecursionStep(root.right, result);
        result.add(root.val);
    }
    /** 后序遍历: 非递归 新方法*/
    public static List<Integer> postOrder(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null)
            return result;

        // 因为处理过程中栈的储栈结果与后续遍历刚好相反 所以使用一个栈暂出栈结果
        Stack<Integer> resultStack = new Stack<Integer>(); // 栈暂出栈结果
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();  // stack出栈顺序为"根右左"与后序顺序"左右根"刚好相反
        BinaryTreeNode node = root;
        stack.push(node);
        while(!stack.isEmpty()) {
            node = stack.pop();
            resultStack.push(node.val);    // * 遍历该结点
            // 先左后右 出栈顺序为"根右左"
            if(node.left != null)
                stack.push(node.left);
            if(node.right != null) {
                stack.push(node.right);
            }
        }

        // 先用栈存储结果再导入到list中
        while(!resultStack.empty())
            result.add(resultStack.pop());
        /*
        // 也可直接加到列表再翻转
        result.add(node.val);         // * 处改写
        Collections.reverse(result);  // 翻转结果
        */
        return result;
    }
    /** 后序遍历：非递归  */
    public static List<Integer> postOrderOld(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null){
            return result;
        }
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        BinaryTreeNode node = root;
        BinaryTreeNode lastNode = root;     // 记录访问过结点以避免重复访问右子树 由于是二叉树只需记住最后一个
        while (!stack.isEmpty() || node != null) {
            // 左子树
            while (node != null) {          // 直到底部 游标为空
                stack.push(node);
                node = node.left;
            }
            node = stack.peek();    // 查看栈顶
            // 右子树
            if (node.right == null || node.right == lastNode) { // 右子树为空或已访问过
                // 直接遍历该结点
                result.add(node.val);
                stack.pop();        // 遍历后从栈中退出该结点
                lastNode = node;    // 记录访问过结点
                node = null;
            }
            else {                  // 否则继续向右
                node = node.right;
            }
        }
        return result;
    }

    /**
     * 层序遍历
     */
    public static List<Integer> levelTraversal(BinaryTreeNode root){
        List<Integer> result = new ArrayList<Integer>();
        if(root == null)
            return result;
        // 依次将每层存储到队列中
        Queue<BinaryTreeNode> queue = new LinkedList<BinaryTreeNode>();
        BinaryTreeNode node = root;
        queue.add(node);
        while(!queue.isEmpty()){
            node = queue.poll();
            result.add(node.val);
            if(node.left != null)
                queue.add(node.left);
            if(node.right != null)
                queue.add(node.right);
        }
        return result;
    }


    /** ------------------------------------------- 打印 -------------------------------------------------*/
    /**
     * 分层打印
     * @param root
     * @return ArrayList<ArrayList<Integer>> 每层的结点序列的序列
     */
    public static ArrayList<ArrayList<Integer>> printTree(BinaryTreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        BinaryTreeNode node = root;
        queue.add(node);
        List<Integer> levelList = new ArrayList<>();    // 每层的结点序列
        int levelNum = 1;       // 当前行结点数 初始为1 只有根节点
        int traversedNum = 0;   // 当前行已遍历结点数
        while(!queue.isEmpty()){
            node = queue.poll();
            levelList.add(node.val);
            ++traversedNum;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            // 遍历完当前层
            if (levelNum == traversedNum) {     // 从队列中取出数量=存入数量
                result.add(new ArrayList<Integer>(levelList));      // 必须新建 否则因为是引用传值而结果每层都一样
                levelNum = queue.size();    // 下一行结点数=队列大小
                traversedNum = 0;           // 重置下一行已遍历结点数
                levelList.clear();
            }
        }
        return result;
    }

}
