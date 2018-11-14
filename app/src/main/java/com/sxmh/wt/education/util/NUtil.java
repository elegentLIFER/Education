package com.sxmh.wt.education.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.sxmh.wt.education.MyApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class NUtil {

    /**
     * dp转换成px
     */
    public static int dp2px(float dpValue) {
        float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(float pxValue) {
        float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(float spValue) {
        float fontScale = MyApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(float pxValue) {
        float fontScale = MyApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据微妙获得日期
     *
     * @param sd
     * @return
     */
    public static String getFormatDate(long sd) {
        Date dat = new Date(sd);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String sb = format.format(gc.getTime());
        return sb;
    }

    /**
     * 秒转为时分秒
     *
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String getString(int id, Object... formatArgs) {
        return MyApplication.getInstance().getString(id, formatArgs);
    }

    public static int getColor(int id) {
        return MyApplication.getInstance().getResources().getColor(id);
    }

    public static <T> boolean isListEqual(List<T> listA, List<T> listB) {
        if (listA.size() != listB.size()) {
            return false;
        }
        int size = listA.size();
        for (int i = 0; i < size; i++) {
            if (!listA.get(i).equals(listB.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static ArrayList<Boolean> getANewOptionList() {
        ArrayList<Boolean> booleans = new ArrayList<>();
        booleans.add(false);
        booleans.add(false);
        booleans.add(false);
        booleans.add(false);
        booleans.add(false);
        booleans.add(false);
        return booleans;
    }


    //找到数组中的最大值
    public static int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    /**
     * 7大查找算法：
     * 1.顺序查找 2.二分查找 3.差值查找 4.Fabonacci查找 5.树表查找（二叉查找树，2-3查找树，红黑树，B树和B+树） 6.分块查找 7.哈希查找
     */
    public static void main(String[] args) {
//        int[] intArr = {1, 2, 3, 4, 6, 23, 65, 88, 96, 456, 1200};
////        int index = sequenceSearch0(intArr,1);
////        int index = binarySearch0(intArr,88);
////        int index = binarySearch1(intArr, 96, 0, intArr.length - 1);
////        int index = insertionSearch0(intArr, 98);
//
//        int[] fabonacciArr = new int[20];
//        constructFabonacciArray(fabonacciArr);
//        int index = fabonacciSearch0(intArr, fabonacciArr, 88);
//        System.out.println(Arrays.toString(fabonacciArr));
//        System.out.println(index);

//        int[] intArr = {45, 26, 78, 1, 32, 158, 95, 34, 57, 86, 24, 21, 16, 3, 88, 86, 54, 166, 259, 100, 12, 54, 78, 199, 0};
//        insertSort0(intArr);
//        System.out.println(Arrays.toString(intArr));

        ListNode listNode = new ListNode(0);
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode7 = new ListNode(7);

        listNode.next = listNode1;
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        listNode6.next = listNode7;
        listNode7.next = listNode1;

        System.out.println(JumpFloorII(1));

    }


    /**
     * 跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     */
    public static int JumpFloor(int target) {
        if (target == 0) return 0;
        if (target == 1) return 1;
        if (target == 2) return 2;

        int n1 = 1;
        int n2 = 2;
        int total = 0;
        for (int i = 3; i <= target; i++) {
            total = n1 + n2;
            n1 = n2;
            n2 = total;
        }

        return total;
    }

    /**
     * 变态跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     */
    public static int JumpFloorII(int target) {
        if (target <= 0) {
            return -1;
        } else if (target == 1) {
            return 1;
        } else {
            return 2 * JumpFloorII(target - 1);
        }
    }

    /**
     * 合并两个有序的List成一个  非递归
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode headNode = new ListNode(0);
        ListNode curNode = headNode;
        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                curNode.next = list2;
                list2 = list2.next;
            } else {
                curNode.next = list1;
                list1 = list1.next;
            }
            curNode = curNode.next;
        }

        if (list1 != null) {
            curNode.next = list1;
        }
        if (list2 != null) {
            curNode.next = list2;
        }

        return headNode.next;
    }

    /**
     * 合并两个有序的List成一个  递归版本
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode MergeInRecurse(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        if (list1.val < list2.val) {
            list1.next = MergeInRecurse(list1.next, list2);
            return list1;
        } else {
            list2.next = MergeInRecurse(list2.next, list1);
            return list2;
        }
    }

//    /**
//     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。假设压入栈的所有数字均不相等。
//     * 例如1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序
//     *
//     * @return
//     */
//    public boolean IsPopOrder(int[] push, int[] pop) {
//        if (push == null || pop == null) return false;
//        int pushLen = push.length;
//        int popLen = pop.length;
//        if (pushLen != popLen) return false;
//
//        Stack<Integer> tempStack = new Stack<>();
//        for (int i = 0; i < pushLen; i++) {
//            int popNum = pop[i];
//            for (int j = 0; j < pushLen; j++) {
//                int push1 = push[j];
//                tempStack.push(push1);
//                if (tempStack) {
//                    tempStack.pop();
//                }
//            }
//        }
//
//    }

//    /**
//     * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
//     *
//     * @param root1
//     * @param root2
//     * @return
//     */
//    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
//        if (root2==null)
//
//
//
//
////        if (root1 == null || root2 == null || root1.val != root2.val) return false;
////        TreeNode current1 = root1;
////        TreeNode current2 = root2;
////        while (current2.left != null) {
////            if (current2.left.val != current1.left.val) return false;
////            current1 = current1.left;
////            current2 = current2.left;
////        }
////
////        TreeNode current3 = root1;
////        TreeNode current4 = root2;
////        while (current4.right != null) {
////            if (current4.right.val != current3.right.val) return false;
////            current3 = current1.right;
////            current4 = current4.right;
////        }
////        while (root2.right != null) {
////
////        }
//    }

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }


    public static boolean hasCycle(ListNode head) {
        if (null == head || null == head.next) {
            return false;
        }

        ListNode slow = head, fast = head;
        while (null != fast && null != fast.next) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                break;
        }
        if (fast != slow) {
            return false;
        }
        return true;
    }

    public static ListNode swapPairs(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;

        ListNode beginNode = head.next;
        ListNode pre = null;
        while (head != null) {
            if (head.next == null) break;
            ListNode temp = head.next;
            head.next = head.next.next;
            if (pre != null) pre.next = temp;
            temp.next = head;
            pre = head;
            head = head.next;
        }
        return beginNode;
    }

    public static ListNode reverseListRecursively(ListNode listNode, ListNode newONe) {
        if (listNode == null) {
            return newONe;
        }
        ListNode next = listNode.next;
        listNode.next = newONe;
        return reverseListRecursively(next, listNode);
    }

    /**
     * 单链表翻转
     */
    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;

        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    private static void insertSort0(int[] intArr) {
        if (intArr == null || intArr.length == 0)
            throw new NullPointerException("array must not be null");
        int length = intArr.length;
        for (int i = 1; i < length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (intArr[j + 1] >= intArr[j]) break;
                int temp = intArr[j + 1];
                intArr[j + 1] = intArr[j];
                intArr[j] = temp;
            }
        }
    }

    private static void constructFabonacciArray(int[] array) {
        array[0] = 1;
        array[1] = 1;
        int m = 2;
        while (m < 20) {
            array[m] = array[m - 1] + array[m - 2];
            m++;
        }
    }

//    private static int fabonacciSearch0(int[] intArr, int[] fabArr, int i) {
//        int begin = 0;
//        int last = intArr.length - 1;
//        int middle;
//        while (begin <= last) {
//            middle = begin + last;
//        }
//    }

    private static int sequenceSearch0(int[] intArr, int i) {
        int index = 0;
        while (intArr[index] != i) {
            index++;
            if (index == intArr.length) {
                return -1;
            }
        }
        return index;
    }

    private static int binarySearch0(int[] intArr, int i) {
        int begin = 0;
        int last = intArr.length - 1;
        int middle;
        while (begin <= last) {
            middle = (last + begin) >>> 2;
            int key = intArr[middle];
            if (key < i) {
                begin = middle + 1;
            } else if (key > i) {
                last = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    // recursion
    private static int binarySearch1(int[] intArr, int i, int begin, int last) {
        int middle = (last + begin) / 2;
        int middleValue = intArr[middle];
        if (middleValue == i) {
            return middle;
        }

        if (middleValue < i) {
            return binarySearch1(intArr, i, middle + 1, last);
        }

        if (middleValue > i) {
            return binarySearch1(intArr, i, begin, last - 1);
        }
        return -1;
    }

    private static int insertionSearch0(int[] intArr, int i) {
        int begin = 0;
        int last = intArr.length - 1;
        int middle;
        while (begin <= last) {
            middle = begin + (i - intArr[begin]) / (intArr[last] - intArr[begin]) * (last - begin);
            int middleValue = intArr[middle];
            if (middleValue == i) {
                return middle;
            }
            if (middleValue < i) {
                begin = middle + 1;
            } else if (middleValue > i) {
                last = middle - 1;
            }
        }
        return -1;
    }


    /**
     * 把batmap 转file
     *
     * @param bitmap
     * @param filepath
     */
    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
