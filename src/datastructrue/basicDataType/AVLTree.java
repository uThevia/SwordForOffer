package datastructrue.basicDataType;

/**
 * AVL树节点类
 */


/**
 * AVL树类
 */
public class AVLTree {
    class AVLNode {
        int val;           // 节点值
        int height;        // 节点高度
        AVLNode left;      // 左子节点
        AVLNode right;     // 右子节点

        /**
         * 构造函数
         */
        public AVLNode(int val) {
            this.val = val;
            this.height = 1;
            this.left = null;
            this.right = null;
        }
    }

    private AVLNode root;   // 根节点

    /**
     * 获取节点高度
     */
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * 获取平衡因子
     */
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * 更新节点高度
     */
    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    /**
     * 右旋
     */
    private AVLNode rotateRight(AVLNode node) {
        AVLNode left = node.left;
        AVLNode right = left.right;

        left.right = node;
        node.left = right;

        updateHeight(node);
        updateHeight(left);

        return left;
    }

    /**
     * 左旋
     */
    private AVLNode rotateLeft(AVLNode node) {
        AVLNode right = node.right;
        AVLNode left = right.left;

        right.left = node;
        node.right = left;

        updateHeight(node);
        updateHeight(right);

        return right;
    }

    /**
     * 插入节点
     */
    public void insert(int val) {
        root = insert(root, val);
    }

    private AVLNode insert(AVLNode node, int val) {
        if (node == null) {
            return new AVLNode(val);
        }
        if (val < node.val) {
            node.left = insert(node.left, val);
        } else {
            node.right = insert(node.right, val);
        }

        // 更新节点高度
        updateHeight(node);

        // 检查平衡因子
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1 && val < node.left.val) {  // LL
            return rotateRight(node);
        }
        if (balanceFactor < -1 && val > node.right.val) {  // RR
            return rotateLeft(node);
        }
        if (balanceFactor > 1 && val > node.left.val) {  // LR
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balanceFactor < -1 && val < node.right.val) {  // RL
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    /**
     * 删除节点
     */
    public void delete(int val) {
        root = delete(root, val);
    }

    private AVLNode delete(AVLNode node, int val) {
        if (node == null) {
            return null;
        }
        if (val < node.val) {
            node.left = delete(node.left, val);
        } else if (val > node.val) {
            node.right = delete(node.right, val);
        } else {
            if (node.left == null || node.right == null) { // 只有一个子节点或者没有子节点
                AVLNode child = node.left == null ? node.right : node.left;
                if (child == null) { // 没有子节点
                    node = null;
                } else { // 只有一个子节点
                    node.val = child.val;
                    node.left = child.left;
                    node.right = child.right;
                }
            } else { // 有两个子节点
                AVLNode minNode = findMin(node.right); // 找到右子树中的最小节点
                node.val = minNode.val;
                node.right = delete(node.right, minNode.val);
            }
        }

        if (node == null) {
            return null;
        }

        // 更新节点高度
        updateHeight(node);

        // 检查平衡因子
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {  // LL
            return rotateRight(node);
        }
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {  // LR
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {  // RR
            return rotateLeft(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {  // RL
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    /**
     * 查找最小节点
     */
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 查找节点
     */
    public boolean search(int val) {
        AVLNode node = root;
        while (node != null) {
            if (val < node.val) {
                node = node.left;
            } else if (val > node.val) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 中序遍历
     */
    public void inorder() {
        inorder(root);
    }

    private void inorder(AVLNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.val + " ");
        inorder(node.right);
    }

    /**
     * 测试程序
     */
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // 插入测试
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        // 中序遍历测试
        System.out.println("中序遍历:");
        tree.inorder();
        System.out.println();

        // 查找测试
        System.out.println("查找节点:");
        System.out.println(tree.search(30));  // true
        System.out.println(tree.search(60));  // false

        // 删除测试
        System.out.println("删除节点:");
        tree.delete(25);
        tree.delete(40);
        System.out.println("中序遍历:");
        tree.inorder();
        System.out.println();
    }
}

