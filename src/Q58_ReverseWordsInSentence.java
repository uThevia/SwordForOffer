import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。
 * 为简单起见，标点符号和普通字母一样处理。
 *
 * 说明:
 * 无空格字符构成一个单词
 * 去除首尾和单词间多余空格,
 */
public class Q58_ReverseWordsInSentence {
    /**
     * 简单 调用库	O(n), O(n)
     */
    private static final Character SPACE = ' ';
    public static String simple(String s) {
        String[] strings = s.trim().split("\\s+");
        StringBuilder res = new StringBuilder();
        // 避免最后多加1个空格
        for (int i = strings.length - 1; i >= 1 ; i--) {
            res.append(strings[i]).append(SPACE);
        }
        res.append(strings[0]);
        return res.toString();
    }

    /**
     * 双端队列	O(n), O(n)
     * 翻转单词顺序单词相当于后入先出
     */
    public static String deque(String s) {
        int l = 0, r = s.length() - 1;
        // 跳过开头空格
        while (l <= r && s.charAt(l) == SPACE) {
            ++l;
        }
        // 跳过结尾空格
        while (l <= r && s.charAt(r) == SPACE) {
            --r;
        }
        Deque<String> deque = new ArrayDeque<String>();
        StringBuilder word = new StringBuilder();
        while (l <= r) {
            char c = s.charAt(l);
            // 将单词加到队列中
            if ((word.length() != 0) && (c == SPACE)) {
                deque.offerFirst(word.toString());
                word.setLength(0);  // 清空单词
            } else if (c != SPACE) {
                word.append(c);
            }
            ++l;
        }
        deque.offerFirst(word.toString());
        return String.join(String.valueOf(SPACE), deque);
    }

    /**
     * M 双指针法	O(n), O(n)
     * 倒序读取 暂存单词 遇到空格就输出单词+空格
     * "暂存单词"通过双指针指向单词的开头(不包含即指向前个空格)和结尾
     */
    public static String towPointers(String s) {
        s = s.trim();
        StringBuilder res = new StringBuilder();
        int l = s.length() - 1;
        int r;
        // 跳过尾部空格
        while (l >= 0 && s.charAt(l) == SPACE) {
            --l;
        }
        r = l;
        // 包含: 跳过头部空格, 最后一个单词
        while (l >= 0) {
            while (l >= 0 && s.charAt(l) != SPACE) {
                --l;
            }
            res.append(s.substring(l + 1, r + 1)).append(SPACE);  // s[l+1:r]
            while (l >= 0 && s.charAt(l) == SPACE) {
                --l;
            }
            r = l;
        }
        // 删去最后多加的1个空格
        return res.deleteCharAt(res.length() - 1).toString();
    }

    public static void main(String[] args) {
        String s = "  abc de  fghi  jk   ";
        System.out.println(simple(s));
        System.out.println(deque(s));
        System.out.println(towPointers(s));

        int k = 5;
        System.out.println(LeftRotateString.simple(s, k));
        System.out.println(LeftRotateString.loop(s, k));
    }
}

/**
 *
 */
class LeftRotateString {
    /**
     * 简单 切片	O(n), O(n)
     */
    public static String simple(String s, int k) {
        int n = s.length();
        if (n == 0) {
            return s;
        }
        k = k % n;
        return s.substring(k, n) + s.substring(0, k);
    }

    /**
     * 遍历拼接	O(n), O(n)
     */
    public static String loop(String s, int k) {
        int n = s.length();
        if (n == 0) {
            return s;
        }
        k = k % n;
        StringBuilder res = new StringBuilder();
        for(int i = k; i < n; i++) {
            res.append(s.charAt(i));
        }
        for(int i = 0; i < k; i++) {
            res.append(s.charAt(i));
        }
        return res.toString();
    }
}