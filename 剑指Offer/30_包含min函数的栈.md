## 30_包含min函数的栈
Q30_MinInStack

定义栈的数据结构，实现一个得到最小元素的min函数在该栈中
调用min, push, pop的时间复杂度都是O(1)

**思路**
在标准栈添加属性min保存, 每次push和pop时更新min, (且需要一个min计数在pop最小值后判断是否还有最小值, 需要保存次小值)
	在pop非重复最小值后如何更新次小值
用辅助栈存储当前最小值, 由于栈是先入后出的, 所以底下m个元素的最小值是固定的保存在栈的底下向上第m位置

```java
/**
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
```

**总结**
举例让抽象问题具体化

Integer的equals重写过，比较的是内部value的值， ==如果在[-128,127]会被cache缓存,超过这个范围则比较的是对象是否相同

