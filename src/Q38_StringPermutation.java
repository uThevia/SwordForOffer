import java.util.*;

/**
 * 字符串的排列
 */
public class Q38_StringPermutation {
    /** ---------------------------------------- M1 回溯法 --------------------------------------- */
    /**
     * M1 递归+回溯	O(n!n), 排序法 O(n) / 记录法 O(n^2)
     * 排列看成是一行空位, 依次填充字符: f(n)=x+f(n-1)
     * 逐个从剩余字符中取字符作为首位字符 , 重复字符只能填1次
     * 避免重复字符方法:
     * 排序法 通过排序后字符数组中 对应访问标志和重复字符位置关系
     *      chars原地交换即为单个结果
     * 记录法 通过Set记录下已使用过的字符种类
     *      通过StringBuilder生成单个结果
     */
    /** -------------------- 记录法 -------------------- */
    /**
     * 记录法  O(n!n), O(n^2)
     * 不重复通过先将所有字符排序, 也可记录当前位置已填充过的字符 重复时跳过
     * @param s
     * @return
     */
    public static String[] backtrackRecord (String s) {
        int n = s.length();
        List<String> res = new ArrayList<String>();
        char[] chars = s.toCharArray();                         // 字符串中的字符的数组
        backtrackRecordRecursion(chars, res, n, 0);
        return res.toArray(new String[res.size()]);     // List转Array
    }

    private static void backtrackRecordRecursion(char[] chars, List<String> res, int n, int i) {
        // 基例: 满足生成条件
        if (i == n - 1 ) {  // 只剩下末尾位置, 待选字符剩下最后1个 剩余字符为空
            res.add(String.valueOf(chars));
            return;
        }

        Set<Character> set = new HashSet<>();   // 保存当前位置i使用过的字符种类. 长度n就需要n个Set
        for (int j = i; j < n; j++) {
            // 剪枝: 跳过字符 字符种类使用过
            if (set.contains(chars[j])) {
                continue;
            }
            // 当前位置i使用当前字符
            set.add(chars[j]);
            swap(chars, i, j);
            // 递归下个位置 i+1
            backtrackRecordRecursion(chars, res, n, i + 1);
            // 回溯: 恢复顺序
            swap(chars, i, j);
        }
    }

    /** -------------------- 排序法 -------------------- */
    /**
     * 排序法  O(n!n), O(n)
     * 不重复实现通过先将所有字符排序, 选取重复字符时只选取首个未使用字符
     */
    public static String[] backtrackSort (String s) {
        int n = s.length();
        List<String> res = new ArrayList<String>();
        char[] chars = s.toCharArray();                         // 字符串中的字符的数组
        Arrays.sort(chars);     // 排序字符数组
        boolean[] charsVisited = new boolean[n];     // 字符数组中每个字符是否已使用过
        StringBuilder stringBuilder = new StringBuilder();      // 排列结果中的单个字符串

        backtrackSortRecursion(chars, charsVisited, stringBuilder, res, n, 0);
        return res.toArray(new String[res.size()]);     // List转Array
    }

    /**
     *
     * @param chars             字符串中的字符的数组
     * @param charsVisited      字符数组中每个字符是否已使用过
     * @param stringBuilder     排列结果中的单个字符串 生成中
     * @param res               最终结果    生成中
     * @param n     字符串长度   n = chars.length;
     * @param i     生成第i个位置 = 当前生成字符串长度   i = stringBuilder.length();
     */
    private static void backtrackSortRecursion (char[] chars, boolean[] charsVisited, StringBuilder stringBuilder, List<String> res, int n, int i) {
        // 基例: 满足生成条件
        if (i == n ) {  // i越界
            res.add(stringBuilder.toString());
            return;
        }

        for (int j = 0; j < n; j++) {
            // 跳过字符: 字符使用过 或 重复字符的非首个未使用字符
            if (charsVisited[j] || (j != 0 && chars[j] == chars[j-1] && !charsVisited[j-1])) {    // j != 0 防止[j-1]越界
                continue;
            }

            // 使用当前字符
            charsVisited[j] = true;
            stringBuilder.append(chars[j]);
            // 递归下个位置 i+1
            backtrackSortRecursion(chars, charsVisited, stringBuilder, res, n, i + 1);
            // 回溯: 删除当前字符
            stringBuilder.deleteCharAt(i);
            charsVisited[j] = false;
        }
    }

    /** ---------------------------------------- M2 下个排列法 --------------------------------------- */
    /**
     * M2 下个排列	O(n!n), O(1)
     * (按字典序)当前排列的下个排列 O(n)
     * 优点是无需去重
     * chars原地交换即为单个结果
     */
    public static String[] nextPermutation(String s) {
        List<String> res = new ArrayList<String>();
        char[] chars = s.toCharArray();
        Arrays.sort(chars); // 排序字符数组 使之为第一个排列
        do {
            res.add(new String(chars));
        } while (nextPermutation(chars));
        return res.toArray(new String[res.size()]);
    }

    /**
     * 是否存在下个排列 并将字符数组值改为下个排列
     */
    private static boolean nextPermutation(char[] chars) {
        int n = chars.length;

        // 逆向寻找字符串中第一个正序的字符: chars[i] < chars[i + 1]. 如edacb的a
        int i = n - 2;  // 逆向第一个正序字符的下标
        while (i >= 0 && chars[i] >= chars[i + 1]) {
            i--;
        }
        // 不存在下个排列
        if (i < 0) {    // i越界 不存在正序字符
            return false;
        }
        // 存在下个排列: 找到i后首个大于i的字符将其作为i处并从完全正序重新开始
        // 逆向寻找字符串中首个大于字符i的字符: chars[j] > chars[i]. 如edacb的b
        int j = n - 1;
        while (j >= 0 && chars[j] <= chars[i]) {
            j--;
        }
        // 交换i,j 并反转i+1:n-1: 因为i是首个正序 从i+1到尾部是完全逆序的 更新i后则其后应从完全正序重新开始
        swap(chars, i, j);
        reverse(chars, i + 1, n - 1);
        return true;
    }
    /** 交换 */
    private static void swap(char[] chars, int i, int j) {
        /*
        int n;
        if (chars == null || (n = chars.length) == 0 || i < 0 || i >= n || j < 0 || j >= n ) {
            return;
        }
        if (i == j) {
            return;
        }
        */
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
    /** 反转 char[left:right]*/
    private static void reverse(char[] chars, int left, int right) {
        /*
        int n;
        if (chars == null || (n = chars.length) == 0 || left < 0 || left >= n || right < 0 || right >= n ) {
            return;
        }
        */
        while (left < right) {
            swap(chars, left, right);
            left++;
            right--;
        }
    }

    /**
     * 引申问题:
     * 字符串的组合 O(2^n), O(n)
     * 单个组合及是所有组合结果都按照字典序
     * n个字符的m长组合 f(n,m) = f(n-1,m-1)+f(n-1,m)
     */
    /**
     * 排序法
     * 不重复实现通过先将所有字符排序, 选取重复字符时只选取首个未使用字符
     */
    public static String[] combinationSort (String s) {
        int n = s.length();
        List<String> res = new ArrayList<String>();
        char[] chars = s.toCharArray();                         // 字符串中的字符的数组
        Arrays.sort(chars);     // 排序字符数组
        boolean[] charsVisited = new boolean[n];     // 字符数组中每个字符是否已使用过
        StringBuilder stringBuilder = new StringBuilder();      // 排列结果中的单个字符串

        combinationSortRecursion(chars, charsVisited, stringBuilder, res, n, 0);
        return res.toArray(new String[res.size()]);     // List转Array
    }

    /**
     * 排序法 递归 dfs
     * 单个组合有序 所以区别于排列时:
     *      从剩余字符中取字符必须不小于当前字符;
     *      保存单个结果是在添加字符时 而不是达到长度n
     * @param chars             字符串中的字符的数组
     * @param charsVisited      字符数组中每个字符是否已使用过
     * @param stringBuilder     排列结果中的单个字符串 生成中
     * @param res               最终结果    生成中
     * @param n     字符串长度   n = chars.length;
     * @param i     生成第i个位置 = 当前生成字符串长度   i = stringBuilder.length();
     */
    private static void combinationSortRecursion (char[] chars, boolean[] charsVisited, StringBuilder stringBuilder, List<String> res, int n, int i) {
        // 基例: 用到所有字符的最长组合
        if (i == n ) {  // i越界
            return;
        }
        for (int j = i; j < n; j++) {
            // 跳过字符: 字符使用过 或 重复字符的非首个未使用字符 或 字符小于组合结果末尾字符
            if (charsVisited[j] || (j != 0 && chars[j] == chars[j-1] && !charsVisited[j-1]) || (i != 0 && chars[j] < stringBuilder.charAt(i - 1))) {    //
                continue;
            }

            // 使用当前字符
            charsVisited[j] = true;
            stringBuilder.append(chars[j]);
            res.add(stringBuilder.toString());
            // 递归下个位置 i+1
            combinationSortRecursion(chars, charsVisited, stringBuilder, res, n, i + 1);
            // 回溯: 删除当前字符
            stringBuilder.deleteCharAt(i);
            charsVisited[j] = false;
        }
    }


    public static void main(String[] args) {
        String s = "abbcdd";      // abbc
        // 字符串的排列
        System.out.println(Arrays.toString(backtrackRecord(s)));
        System.out.println(Arrays.toString(backtrackSort(s)));
        System.out.println(Arrays.toString(nextPermutation(s)));
        // 字符串的组合
        System.out.println(Arrays.toString(combinationSort(s)));
    }
}
