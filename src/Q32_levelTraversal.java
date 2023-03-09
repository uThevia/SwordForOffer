import utils.BinaryTreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q32_levelTraversal {
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
            if(node.left != null) {
                queue.add(node.left);
            }
            if(node.right != null) {
                queue.add(node.right);
            }
        }
        return result;
    }
}
