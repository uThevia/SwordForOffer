import java.util.Stack;

/**
 * 用两个栈实现队列
 */
public class Q09_QueueByTwoStack<T> {
    private  Stack<T> stack1;   // 插入栈
    private  Stack<T> stack2;   // 删除栈

    public Q09_QueueByTwoStack() {
        stack1 = new Stack<T>();
        stack2 = new Stack<T>();
    }

    public void offer(T value){   // 静态方法需声明T静态
        stack1.push(value);
    }

    public T poll(T value){
        if (stack2.isEmpty()) {         // 删除栈空
            if (stack1.isEmpty()) {     // 插入栈空
                return null;
            }
            else {                     // 插入栈非空
                // 将插入栈元素都插入到删除栈中
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
        }
        return stack2.pop();
    }
}
