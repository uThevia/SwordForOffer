import datastructrue.Stack;

/**
 * 包含min函数的栈
 * 定义栈的数据结构，实现一个得到最小元素的min函数在该栈中
 * 调用min, push, pop的时间复杂度都是O(1)
 */
public class Q30_MinInStack<T extends Comparable<T>> extends Stack<T>{  // 由于自实现Stack的泛型T没有约束Comparable<T>, 需要在本类声明约束
    // 辅助栈: 保存栈中按先入顺序(底部)每个元素及之前元素对应的最小元素
    private Stack<T> minInStack;

    public Q30_MinInStack() {
        super();
        minInStack = new Stack<T>();
    }

    @Override
    public void push(T item) {
        super.push(item);
        T minItem = item.compareTo(minInStack.peek()) < 0 ? item : minInStack.peek();
        minInStack.push(minItem);
    }

    @Override
    public T pop() {
        minInStack.pop();   // Stack.pop(): 空栈返回null
        return super.pop();
    }

    public T min(){
        return minInStack.peek();
    }
}
