package datastructrue;

import java.util.*;

public class AVLTree<T extends Comparable<T>> {
    // 树节点
    private class Node {
        T data; // 节点数据
        Node left; // 左子节点
        Node right; // 右子节点
        int height; // 节点高度

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root; // 根节点

    // 获取节点高度
    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    // 获取节点的平衡因子
    private int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // 更新节点高度
    private void updateHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // 左旋
    private Node leftRotate(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    // 右旋
    private Node rightRotate(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    // 插入节点
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node insert(Node node, T data) {
        if (node == null) {
            return new Node(data);
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = insert(node.left, data);
        } else if (cmp > 0) {
            node.right = insert(node.right, data);
        } else {
            // 如果相等，不进行任何操作
            return node;
        }

        updateHeight(node);
        int bf = balanceFactor(node);

        // 进行平衡调整
        if (bf > 1 && data.compareTo(node.left.data) < 0) {
            // LL情况，进行右旋
            node = rightRotate(node);
        } else if (bf < -1 && data.compareTo(node.right.data) > 0) {
            // RR情况，进行左旋
            node = leftRotate(node);
        } else if (bf > 1 && data.compareTo(node.left.data) > 0) {
            // LR情况，先对左子树进行左旋，再对当前节点进行右旋
            node.left = leftRotate(node.left);
            node = rightRotate(node);
        } else if (bf < -1 && data.compareTo(node.right.data) < 0) {
            // RL情况，先对右子树进行右旋，再对当前节点进行左旋
            node.right = rightRotate(node.right);
            node = leftRotate(node);
        }
        return node;
    }

    // 删除节点
    public void delete(T data) {
        root = delete(root, data);
    }

    private Node delete(Node node, T data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = delete(node.left, data);
        } else if (cmp > 0) {
            node.right = delete(node.right, data);
        } else {
            if (node.left == null && node.right == null) {
                // 叶子节点，直接删除
                node = null;
            } else if (node.left == null) {
                // 只有右子树，将右子树提上来
                node = node.right;
            } else if (node.right == null) {
                // 只有左子树，将左子树提上来
                node = node.left;
            } else {
                // 有两个子节点，找到后继节点替代当前节点
                Node successor = findMin(node.right);
                node.data = successor.data;
                node.right = delete(node.right, successor.data);
            }
        }

        if (node == null) {
            return null;
        }

        updateHeight(node);
        int bf = balanceFactor(node);

        // 进行平衡调整
        if (bf > 1 && balanceFactor(node.left) >= 0) {
            // LL情况，进行右旋
            node = rightRotate(node);
        } else if (bf > 1 && balanceFactor(node.left) < 0) {
            // LR情况，先对左子树进行左旋，再对当前节点进行右旋
            node.left = leftRotate(node.left);
            node = rightRotate(node);
        } else if (bf < -1 && balanceFactor(node.right) <= 0) {
            // RR情况，进行左旋
            node = leftRotate(node);
        } else if (bf < -1 && balanceFactor(node.right) > 0) {
            // RL情况，先对右子树进行右旋，再对当前节点进行左旋
            node.right = rightRotate(node.right);
            node = leftRotate(node);
        }

        return node;
    }

    // 查找节点
    public boolean contains(T data) {
        return contains(root, data);
    }

    private boolean contains(Node node, T data) {
        if (node == null) {
            return false;
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            return contains(node.left, data);
        } else if (cmp > 0) {
            return contains(node.right, data);
        } else {
            return true;
        }
    }

    // 查找最小节点
    private Node findMin(Node node) {
        if (node.left == null) {
            return node;
        }
        return findMin(node.left);
    }

    // 中序遍历
    public List<T> inorderTraversal() {
        List<T> res = new ArrayList<>();
        inorderTraversal(root, res);
        return res;
    }

    private void inorderTraversal(Node node, List<T> res) {
        if (node != null) {
            inorderTraversal(node.left, res);
            res.add(node.data);
            inorderTraversal(node.right, res);
        }
    }

    // 测试程序
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();

        // 插入节点
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        tree.insert(4);
        tree.insert(5);

        // 遍历
        List<Integer> inorder = tree.inorderTraversal();
        System.out.println("Inorder traversal:");
        System.out.println(inorder);

        // 查找节点
        System.out.println("Contains 3: " + tree.contains(3));
        System.out.println("Contains 6: " + tree.contains(6));

        // 删除节点
        tree.delete(1);
        tree.delete(4);
        inorder = tree.inorderTraversal();
        System.out.println("Inorder traversal after deletion:");
        System.out.println(inorder);
    }
}


