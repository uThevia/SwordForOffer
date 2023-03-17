import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

public class Test {

    private static void test(int n) {
        IntConsumer consumer = value -> value = value * 2;
        consumer.accept(n);
    }

    private static void test(int value, IntConsumer consumer) {
        consumer.accept(value);
    }

    public static void main(String[] args) {
        int n = 2;
        AtomicInteger  atomicInteger = new AtomicInteger(n);
        test(atomicInteger);
        System.out.println(atomicInteger);

        test(n);
        System.out.println(n);

        test(n, value -> value = value * 2);
        System.out.println(n);

    }
}
