package datastructrue;

import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> {
    private class Node {
        T data;
        Node left;
        Node right;

        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    // 插入节点
    public void insert(T data) {
        root = insertNode(root, data);
    }

    // 递归插入节点
    private Node insertNode(Node node, T data) {
        if (node == null) {
            return new Node(data);
        }

        if (data.compareTo(node.data) < 0) {
            node.left = insertNode(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insertNode(node.right, data);
        }

        return node;
    }

    // 删除节点
    public void remove(T data) {
        root = removeNode(root, data);
    }

    // 递归删除节点
    private Node removeNode(Node node, T data) {
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.data) < 0) {
            node.left = removeNode(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = removeNode(node.right, data);
        } else {
            // 找到要删除的节点
            if (node.left == null && node.right == null) {
                // 要删除的节点没有子节点
                node = null;
            } else if (node.left == null) {
                // 要删除的节点只有右子节点
                node = node.right;
            } else if (node.right == null) {
                // 要删除的节点只有左子节点
                node = node.left;
            } else {
                // 要删除的节点有两个子节点，用右子树的最小值替换要删除的节点
                Node minRight = findMin(node.right);
                node.data = minRight.data;
                node.right = removeNode(node.right, minRight.data);
            }
        }

        return node;
    }

    // 查找最小值
    public T findMin() {
        if (root == null) {
            return null;
        }

        Node minNode = findMin(root);
        return minNode.data;
    }

    // 递归查找最小值
    private Node findMin(Node node) {
        if (node.left == null) {
            return node;
        }

        return findMin(node.left);
    }

    // 查找最大值
    public T findMax() {
        if (root == null) {
            return null;
        }

        Node maxNode = findMax(root);
        return maxNode.data;
    }

    // 递归查找最大值
    private Node findMax(Node node) {
        if (node.right == null) {
            return node;
        }

        return findMax(node.right);
    }

    // 搜索节点
    public boolean search(T data) {
        return searchNode(root, data);
    }

    // 递归搜索节点
    private boolean searchNode(Node node, T data) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.data) < 0) {
            return searchNode(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            return searchNode(node.right, data);
        } else {
            return true;
        }
    }

    // 中序遍历
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    // 递归中序遍历
    private void inorderTraversal(Node node, List<T> result) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.left, result);
        result.add(node.data);
        inorderTraversal(node.right, result);
    }

    // 测试程序
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        // 插入节点
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(1);
        bst.insert(9);
        bst.insert(4);
        bst.insert(6);
        bst.insert(2);
        bst.insert(8);

        // 中序遍历
        List<Integer> inorder = bst.inorderTraversal();
        System.out.println("中序遍历：" + inorder);

        // 查找最小值和最大值
        System.out.println("最小值：" + bst.findMin());
        System.out.println("最大值：" + bst.findMax());

        // 搜索节点
        System.out.println("搜索 6：" + bst.search(6));
        System.out.println("搜索 10：" + bst.search(10));

        // 删除节点
        bst.remove(4);
        bst.remove(1);
        bst.remove(9);

        // 中序遍历
        inorder = bst.inorderTraversal();
        System.out.println("中序遍历：" + inorder);
    }
}

