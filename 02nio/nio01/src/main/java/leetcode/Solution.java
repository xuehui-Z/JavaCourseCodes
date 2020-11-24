package leetcode;

public class Solution {
    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);
        new Solution().numDecodings("12");
    }

    public int numDecodings(String s) {
        if (s.charAt(0) == '0') return 0;

        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = 1;

        if (s.length()==1) return dp[1];

        for (int i=2; i<s.length(); i++) {
            char c1 = s.charAt(i-1);
            char c2 = s.charAt(i-2);

            if (c1 == '0' && c2 == '0') return 0;
            if (c1 == '0' && c2 > '2') return 0;

            if (c1 == '0' || c2 == '0') {
                dp[i] = c1=='0'? dp[i-2] : dp[i-1];
            } else {
                if (c2 <= '2' && c1 <= '6') {
                    dp[i] = dp[i-2] + dp[i-1];
                } else {
                    dp[i] = dp[i-1];
                }
            }

        }
        return dp[s.length()];
    }

}
