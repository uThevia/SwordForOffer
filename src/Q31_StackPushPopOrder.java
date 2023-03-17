import datastructrue.collection.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 栈的压入弹出序列
 * 输入两个整数序列，第一个序列表示栈的入栈序列，请判断第二个序列是否为该栈的出栈序列。
 * 假设压入栈的所有数字均不相等。
 */
public class Q31_StackPushPopOrder {

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        int n = pushed.length;
        for (int i = 0, j = 0; i < n; i++) {
            stack.push(pushed[i]);
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }

    public boolean validateStackSequences2(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        for(int num : pushed) {
            stack.push(num); // num 入栈
            while(!stack.isEmpty() && stack.peek() == popped[i]) { // 循环判断与出栈
                stack.pop();
                i++;
            }
        }
        return stack.isEmpty();
    }

    public static boolean isPopOrderForPushOrder(int[] pushOrder, int[] popOrder) {
        // # 检查输入
        int pushLength;
        int popLength;
        // 空对象, 长度不一致
        if (pushOrder == null || popOrder == null ||  (pushLength = pushOrder.length) != (popLength = popOrder.length)) {
            return false;
        }
        // 都长度0
        if ((pushLength = pushOrder.length) == 0 && (popLength = popOrder.length) == 0) {
            return true;
        }

        Stack<Integer> stack = new Stack<>();

        int pushIndex = 0;
        int popIndex = 0;
        do {
            /* 不需要: 满足条件的入栈和出栈序列, 当遍历到入栈序列的最后一个元素时 栈中元素全部出栈即为剩余出栈序列, 否则出栈序列不满足条件
            // 压入序列遍历完
            if (pushIndex == pushLength) {
                // 出到空栈
                while (!stack.isEmpty()) {
                    if (stack.pop() != popOrder[popIndex++]) {      // 出栈并右移下标
                        return false;
                    }
                }
                // 栈空且出栈序列为空
                return true;
            }
             */

            // 压栈序列值是出栈序列值剩余首位时直接弹出
            if (pushOrder[pushIndex] == popOrder[popIndex]) {   // 也可以写成 pushOrder[pushIndex++] == popOrder[popIndex++]
                // 没有压入就不用弹出 直接跳过
                ++pushIndex;
                ++popIndex;
                // 循环检查栈顶和出栈序列剩余首位
                while (!stack.isEmpty() && stack.peek() == popOrder[popIndex]) {    // 栈非空 且 栈顶=
                    stack.pop();
                    ++popIndex;
                }
                // 此处栈空代表当前及之前的出栈和入栈序列完美对应:
                // 如果 stack.isEmpty() && popIndex==pushIndex 返回ture, 但是没必要在这里反复判断直接continue交由while及其后面判断
                continue;
            } else {    // 不满足就压入
                stack.push(pushOrder[pushIndex++]); // 压栈并右移下标
            }

        } while (!stack.isEmpty() && pushIndex < pushLength);

        if (popIndex < pushIndex) { // 退出序列没有遍历完; 等效于 !stack.isEmpty()
            return false;
        }else {
            return true;
        }
    }

    public static void main(String[] args) {
        int[] pushOrder = {1, 2, 3, 4, 5};
        int[] popOrder = {4, 5, 3, 2, 1};
        int[] popOrderNot = {4, 3, 5, 1, 2};

        System.out.println(isPopOrderForPushOrder(pushOrder, popOrder));
        System.out.println(isPopOrderForPushOrder(pushOrder, popOrderNot));
    }
}
