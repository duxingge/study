package com.wjx;


import java.util.*;

public class Solution {


    public static void main(String[] args) {
        char[][] cc = new char[][]{{'1', '1', '1'}, {'0', '1', '0'}, {'1', '1', '1'}};
        System.out.println((cc));
    }




    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 是否是子树
     *
     * @param root
     * @param subRoot
     * @return
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (isHeadSubtree(root, subRoot)) {
            return true;
        } else {
            if (root != null) {
                return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
            } else {
                return false;
            }
        }
    }

    public boolean isHeadSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot != null) {
            return false;
        }
        if (root != null && subRoot == null) {
            return false;
        }
        if (root == null && subRoot == null) {
            return true;
        }
        if (root.val != subRoot.val) {
            return false;
        } else {
            return isHeadSubtree(root.left, subRoot.left) && isHeadSubtree(root.right, subRoot.right);
        }
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        byte[][] tags = new byte[grid.length][grid[0].length];
        int rows = grid.length;
        int cols = grid[0].length;
        Queue queue = new ArrayDeque();
        int isLandNum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (tags[i][j] != 1) {
                    if (grid[i][j] == '1') {
                        tag(tags, grid, i, j);
                        isLandNum++;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }

            }
        }
        return isLandNum;
    }

    void tag(byte[][] tags, char[][] grid, int i, int j) {
        if (tags[i][j] == 1) {
            return;
        }
        if (grid[i][j] == '1') {
            tags[i][j] = 1;
            if (i + 1 < grid.length) {
                tag(tags, grid, i + 1, j);
            }
            if (j + 1 < grid[0].length) {
                tag(tags, grid, i, j + 1);
            }
            if (i > 0) {
                tag(tags, grid, i - 1, j);
            }
            if (j > 0) {
                tag(tags, grid, i, j - 1);
            }
        }

    }


    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s.length() < p.length()) {
            return res;
        }
        int[] xNums = new int[26];
        int[] yNums = new int[26];
        int diff = 0;

        char[] chars = p.toCharArray();
        for (int i = 0; i < p.toCharArray().length; i++) {
            yNums[chars[i] - 'a']++;
        }
        char[] sChars = s.toCharArray();
        for (int i = 0; i < p.length(); i++) {
            xNums[sChars[i] - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            diff += minus(xNums[i], yNums[i]);
        }
        if (diff == 0) {
            res.add(0);
        }
        for (int i = 0; i < (sChars.length - p.length()); i++) {
            int x1 = sChars[i] - 'a';
            int x2 = sChars[i + p.length()] - 'a';
            diff -= minus(xNums[x1], yNums[x1]);
            diff -= minus(xNums[x2], yNums[x2]);
            xNums[x1]--;
            xNums[x2]++;
            diff += minus(xNums[x1], yNums[x1]);
            diff += minus(xNums[x2], yNums[x2]);
            if (diff == 0) {
                res.add(i + 1);
            }
        }
        return res;
    }

    int minus(int a, int b) {
        return a > b ? a - b : b - a;
    }


    class Nums {
        int num1;
        int num2;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Nums)) {
                return false;
            }
            Nums nums = (Nums) o;
            if (nums.num1 + nums.num2 != num1 + num2) {
                return false;
            }
            if ((num1 == nums.num1 && num2 == nums.num2) || num1 == nums.num2 && num2 == nums.num1) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num1 + num2);
        }
    }


    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode preHead = new ListNode();
        ListNode from = preHead;
        from.next = head;
        ListNode to = head;
        int num = 1;
        while (to.next != null) {
            if (to.next.val == from.next.val) {
                num++;
            } else {
                if (num == 1) {
                    from.next = to;
                    from = from.next;
                } else {
                    from.next = to.next;
                }
                num = 1;
            }
            to = to.next;
        }
        if (num == 1) {
            from.next = to;
        } else {
            from.next = to.next;
        }
        return preHead.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public int findPeakElement(int[] nums, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) / 2;
        boolean bigL = (mid - 1) >= 0 ? nums[mid] > nums[mid - 1] : true;
        boolean bigR = (mid + 1) > (nums.length - 1) ? true : nums[mid] > nums[mid + 1];

        if (bigL && bigR) {
            return mid;
        }
        int left = findPeakElement(nums, start, mid - 1);
        return left != -1 ? left : findPeakElement(nums, mid + 1, end);
    }
}
