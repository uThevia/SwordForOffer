package datastructrue.collection;

/**
 * 红黑树
 * 性质:
 * 1.每个节点是黑色或红色
 * 2.根节点是黑色
 * 3.叶节点是黑色空节点 (称为黑哨兵)
 * 4.红色结点的子节点是黑色
 * 5.从一个节点到其叶节点的所有路径上黑节点数量相同
 *
 * 性质3在实现中由于空节点无法有属性所以看起来叶结点都是红色
 * 性质4保证根结点到叶结点的最长路径不超过最短路径的2倍
 *
 * 推导性质:
 * 红结点必有2个黑子结点(包括黑空节点)
 * 单支结点只有1种可能 父黑子红
 * 空节点颜色是黑色
 */
public class RBTree<T extends  Comparable<T>> {
    /**
     * 颜色常量
     * false = black, true = red
     */
    private static final boolean BLACK = false;
    private static final boolean RED = true;

    /** 根结点 */
    private RBTreeNode<T> root;

    public RBTree() {}

    public RBTree(RBTreeNode<T> root) {
        this.root = root;
    }


    /**
     * 红黑树结点
     * 值为可比泛型T
     */
    public class RBTreeNode<T extends  Comparable<T>> {
        /**
         * 颜色常量
         * false = black, true = red
         */
        private static final boolean BLACK = false;
        private static final boolean RED = true;

        /**
         * 颜色
         */
        private boolean color = false;

        private T key;
        private RBTreeNode<T> parent;
        private RBTreeNode<T> left;
        private RBTreeNode<T> right;
        
        /**
         * 构造
         */
        public RBTreeNode() {}

        public RBTreeNode(T key) {
            this.key = key;
        }

        public RBTreeNode(boolean color, T key) {
            this.color = color;
            this.key = key;
        }

        public RBTreeNode(boolean color, T key, RBTreeNode<T> parent, RBTreeNode<T> left, RBTreeNode<T> right) {
            this.color = color;
            this.key = key;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        /**
         * getter setter
         */
        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public RBTreeNode<T> getParent() {
            return parent;
        }

        public void setParent(RBTreeNode<T> parent) {
            this.parent = parent;
        }

        public RBTreeNode<T> getLeft() {
            return left;
        }

        public void setLeft(RBTreeNode<T> left) {
            this.left = left;
        }

        public RBTreeNode<T> getRight() {
            return right;
        }

        public void setRight(RBTreeNode<T> right) {
            this.right = right;
        }
    }

    /** -------------------- 颜色 --------------------*/
    /** 自定义颜色方法是为了避免空节点无法直接获取属性的情况 */
    /**
     * 获取结点颜色
     * 空节点是黑色 只当非空红色才是ture
     */
    public boolean getColor(RBTreeNode<T> node){
        return null != node && node.color;
    }
    /**
     * 设置结点颜色
     */
    public void setColor(RBTreeNode<T> node,boolean color){
        if (null != node){
            node.color = color;
        }
    }
    private void setRed(RBTreeNode<T> node){ setColor(node, RED); }
    private void setBlack(RBTreeNode<T> node){ setColor(node, BLACK); }
    public void changeColor(RBTreeNode<T> node){
        if (null != node){
            node.color = !node.color;
        }
    }

    /**
     * 判断结点颜色
     */
    private boolean checkColor(RBTreeNode<T> node, boolean color){
        return node != null && node.color == color;
    }
    /** 判断节点颜色是否为红色 */
    private boolean isRed(RBTreeNode<T> node){
        return checkColor(node, RED);
    }
    /** 判断节点颜色是否为黑色 */
    private boolean isBlack(RBTreeNode<T> node){
        return checkColor(node, BLACK);
    }

    /** -------------------- 旋转 -------------------- */
    /**
     * 左旋
     */
    private void leftRotate(RBTreeNode<T> node) {
        RBTreeNode<T> right = node.right;   // 左旋记录下右子

        // 更新中间移交的子: 右子的左子变为自己的右子
        node.right = right.left;
        if (null != right.left)
            right.left.parent = node;

        // 更新父
        right.parent = node.parent;
        if (null == right.parent) {
            this.root = right;      // 更新树的根结点
        } else {
            if (node.parent.left == node)
                right.parent.left = right;
            else
                right.parent.right = right;
        }

        // 最后更新自己与右子的之间关系
        right.left = node;
        node.parent = right;
    }

    /**
     * 右旋
     */
    private void rightRotate(RBTreeNode<T> node) {
        RBTreeNode<T> left = node.left;   // 右旋记录下左子

        // 更新中间移交的子: 左子的右子变为自己的左子
        node.left = left.right;
        if (null != left.right)
            left.right.parent = node;

        // 更新父
        left.parent = node.parent;
        if (null == left.parent) {
            this.root = left;      // 更新树的根结点
        } else {
            if (node.parent.left == node)
                left.parent.left = left;
            else
                left.parent.right = left;
        }

        // 最后更新自己与右子的之间关系
        left.right = node;
        node.parent = left;
    }

    /** ---------------------------------------- 操作 ---------------------------------------- */
    /** -------------------- 搜索 -------------------- */
    /**
     * 搜索
     */
    public RBTreeNode<T> search(T key){
        return searchLoop(this.root,key);
    }
    /**
     * 从某节点搜索
     * 递归法
     * */
    private RBTreeNode<T> searchRecursion(RBTreeNode<T> node,T key){
        if(null == node) {
            return null;
        }
        int tmp = key.compareTo(node.key);
        if (tmp < 0){
            return searchRecursion(node.left, key);
        } else if (tmp > 0){
            return searchRecursion(node.right, key);
        } else {
            return node;
        }
    }
    /**
     * 从某节点搜索
     * 循环法
     * */
    private RBTreeNode<T> searchLoop(RBTreeNode<T> node,T key){
        int tmp = 0;
        while (null != node){
            tmp = key.compareTo(node.key);
            if (tmp < 0)
                node = node.left;
            else if (tmp > 0)
                node = node.right;
            else
                return node;
        }
        return null;
    }


    /** -------------------- 插入 -------------------- */
    /**
     * 插入值
     */
    public boolean insert(T key) {
        RBTreeNode<T> node = new RBTreeNode<T>(RED, key,null, null,null);
        if(null != node){
            return insert(node);
        }
        return false;
    }
    /**
     * 插入结点
     */
    private boolean insert(RBTreeNode<T> node){
        // 插入结点是红的
        node.color = RED;

        RBTreeNode<T> parent = null;    // 插入结点的父结点

        // 定位插入结点的父结点
        RBTreeNode<T> tmp = this.root;   // 暂存父结点 因为定位过程的循环终止条件是parent=null所以需要另一个变量来存储结果 且不能提前获得子结点来优化判断条件
        int cmp = 0;
        while (null != tmp) {
            parent = tmp;
            cmp = node.key.compareTo(tmp.key);
            if (cmp < 0)
                tmp = tmp.left;
            else if(cmp > 0)
                tmp = tmp.right;
            else        // 已有该值
                return false;
        }

        // 先直接插入
        node.parent = parent;
        if (null == parent)     // 空树指定根结点
            this.root = node;
        else {
            cmp = node.key.compareTo(parent.key);
            if (cmp < 0)
                parent.left = node;
            else if(cmp > 0)
                parent.right = node;
        }

        // 插入复衡
        // 1. 父黑(包括父空) 无需复衡
        if (!isBlack(parent))
            insertFix(node);
        return true;
    }
    /**
     * 插入复衡
     */
    private void insertFix(RBTreeNode<T> node){
        RBTreeNode<T> parent = node.parent;
        RBTreeNode<T> grandparent = null;
        RBTreeNode<T> uncle = null;

        while (null != parent && isRed(parent)) {
            grandparent = parent.parent;    // 祖非空: 父红不可能是根结点
            uncle = (parent == grandparent.left) ? grandparent.right: grandparent.left;

            // 2.1 父红叔红
            if ((null != uncle) && isRed(uncle)){
                // 变色父叔祖
                changeColor(uncle);
                changeColor(parent);
                changeColor(grandparent);
                /*
                setBlack(uncle);
                setBlack(parent);
                setRed(grandparent);
                */
                // 递归检查祖结点
                node = grandparent;
                parent = node.parent;
                continue;
            }

            // 2.2父红叔黑
            // **若祖父新成拐形需先旋父变成直形(新父交换); 逆新旋祖 变色祖及其父**
            // ②③逆新旋父,交换新父后转化为①④
            if (parent == grandparent.left) {
                // ②新右父左 变形成①
                if (node == grandparent.right) {
                    // 逆新旋父
                    leftRotate(parent);
                    // 交换新父
                    RBTreeNode<T> tmp= parent;
                    parent = node;
                    node = tmp;
                }
                // ①新左父左  逆新旋祖变色祖及其父
                rightRotate(grandparent);
                changeColor(grandparent.parent);
                changeColor(grandparent);
                /*
                // ①新左父左  右旋祖变色父祖
                leftRotate(grandparent);
                setBlack(parent);
                setRed(grandparent);
                 */
            }
            else {  // parent == grandparent.right
                // ③新左父右 变形成④
                if (node == grandparent.left) {
                    // 逆新旋父
                    rightRotate(parent);
                    // 交换新父
                    RBTreeNode<T> tmp= parent;
                    parent = node;
                    node = tmp;
                }

                // ④新右父右  逆新旋祖变色父及其父
                leftRotate(grandparent);
                changeColor(grandparent.parent);
                changeColor(grandparent);
                /*
                // ④新右父右  左旋祖变色父祖
                leftRotate(grandparent);
                setBlack(node);
                setRed(grandparent);
                 */

            }
            parent = node.parent;
        }
        setBlack(this.root);
    }


    /** -------------------- 删除 -------------------- */
    /**
     * 删除值
     */
    public boolean  remove(T key) {
        RBTreeNode<T> node = search(key);
        if (null != node){
            remove(node);
            return true;
        }

        return false;
    }

    /**
     * 插入结点
     */
    private void remove(RBTreeNode<T> node){
        /* 从remove(T key)进来不可能 node == node
        if (node == node)
            return;
        */

        RBTreeNode<T> parent= node.parent;
        RBTreeNode<T> child;

        // # 删除后继结点: 结点有2子时
        if (null != node.left && null != node.right){
            // 获取被删除节点的后继节点: 后继结点不可能为空
            RBTreeNode<T> replace = node.right;
            while(null != replace.left){
                replace = replace.left;
            }

            // ## 将后继结点的值复制到结点
            node.key = replace.key;
            /* 与上等效
            // 将后继代替结点
            // 建立结点父和后继的关系
            replace.parent = node.parent;
            if (null == parent)    // node是根结点
                this.root = replace;
            else {
                if (parent.left == node)
                    parent.left = replace;
                else
                    parent.right = replace;
            }
            // 建立结点右子和后继的关系
            replace.right = node.right;
            // if (null != node.right)
            node.right.parent = replace;
            // 建立结点左子和后继的关系
            replace.left = node.left;
            node.left.parent = replace;

            replace.color = node.color;
            // 删除结点
            node = null;
             */


            // ## 后继从原位置删除
            /*
            // 也可以直接调用以简化代码 下述代码是利用后继与其父子的特殊关系来简化判断过程
            remove(replace);
             */
            // 建立后继的父和子的关系
            RBTreeNode<T> replace_child = replace.right;    // 后继的子: 后继的可能非空子一定是右子
            RBTreeNode<T> replace_parent = replace.parent;  // 后继的父: 一定不空(向上回溯至多是结点)
            if (null != replace_child){
                replace_child.parent = replace_parent;
            }
            replace_parent.left = replace_child;            // 后继子一定是后继父的左子
            // 删除复衡
            if (replace.color == BLACK){    // 删的结点即后继是黑色才需复衡
                removeFix(replace_child, replace_parent);
            }

            return;
        }

        // # 删除结点: 结点不是有2子时
        // parent = node.parent;    // parent == null iff node是根结点
        child = (null == node.left) ? node.right : node.left;

        // ## 子结点代替结点
        // 子的父是父
        if (null != child){
            child.parent = parent;
        }
        // 父的子是子
        if (null == parent)     // 结点是根结点
            this.root = child;
        else {
            if (parent.left == node)
                parent.left = child;
            else
                parent.right = child;
        }

        // 删除复衡
        if (node.color == BLACK) // 删黑才需要复衡
            removeFix(child, parent);

        // 1. 删红
        // 直接删除
        node = null;
    }

    /**
     * 删除复衡
     * node 删除后顶替的新结点
     * parent node的父结点
     * node哪怕是空也需要复衡
     */
    private void removeFix(RBTreeNode<T> node, RBTreeNode<T> parent){

        RBTreeNode<T> other;
        //node不为空且为黑色，并且不为根节点
        while ((null == node || isBlack(node)) && (node != this.root) ){
            //node是父节点的左孩子
            if (node == parent.left){
                //获取到其右孩子
                other = parent.right;
                //node节点的兄弟节点是红色
                if (isRed(other)){
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                //node节点的兄弟节点是黑色，且兄弟节点的两个孩子节点也是黑色
                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))){
                    setRed(other);
                    node = parent;
                    parent = getParent(node);
                } else {
                    //node节点的兄弟节点是黑色，且兄弟节点的右孩子是红色
                    if (null == other.right || isBlack(other.right)){
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    //node节点的兄弟节点是黑色，且兄弟节点的右孩子是红色，左孩子是任意颜色
                    setColor(other, getColor(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {
                other = parent.left;
                if (isRed(other)){
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((null == other.left || isBlack(other.left)) &&
                        (null == other.right || isBlack(other.right))){
                    setRed(other);
                    node = parent;
                    parent = getParent(node);
                } else {
                    if (null == other.left || isBlack(other.left)){
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    setColor(other,getColor(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }
        if (node != null)
            setBlack(node);

    }

    private RBTreeNode getParent(RBTreeNode<T> node){
        return (node != null) ? node.parent : null;
    }

}
