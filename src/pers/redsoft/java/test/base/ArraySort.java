package pers.redsoft.java.test.base;

import java.util.Arrays;

/**
 * 数组排序的方法以及数组内存比较
 * 
 * @author redsoft
 *
 */
public class ArraySort {

	/**
	 * 主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 初始化int数组
		int num[] = new int[] { 18, 3, 10, 12, 7, 1, 11, 9, 0, 17, 20, 14, 5, 2, 4, 19, 16, 13, 8, 15, 6, 17, -1 };
		// 给numCopy传递int数组的方法之一：使用数组的clone。如int numCopy =
		// num时，numCopy改变时会改变num的值。
		int numCopy[] = num.clone();
		// 循环数组的每一个值
		for (int i = 0; i < num.length; i++) {
			// 循环当前数后面的值
			for (int j = i; j < num.length; j++) {
				// 如果当前数大于后面的数
				if (num[i] > num[j]) {
					// 记住当前数
					int numTemp = num[i];
					// 把当前数变更为后面的数
					num[i] = num[j];
					// 把后面的数变更为当前数，即前后对换
					num[j] = numTemp;
				}
			}
		}
		// 打印num的值，即冒泡排序过的值
		printNum(num);
		// 打印numCopy的值，即拷贝原始数据的值
		printNum(numCopy);
		// 用java自带方法sort进行排序
		Arrays.sort(numCopy);
		// 打印numCopy的值，即利用java自带方法sort过的值
		printNum(numCopy);
		// 虽然排序的结果一样，但是利用clone方法生成的numCopy和num内存地址不一样
		System.out.println(num == numCopy ? "==" : "!=");
		// 虽然排序的结果一样，数组的equals方法其实内部还是使用了==进行比较，所以内存地址不一样
		System.out.println(num.equals(numCopy) ? "equals" : "not equals");

		int numRandom[] = randomCommon(1, 10000, 10000);
		Arrays.sort(numRandom);
		System.out.println(Arrays.toString(numRandom));
		System.out.println(numRandom.length);
		// printNum(numRandom);
	}

	/**
	 * 打印数组
	 * 
	 * @param num
	 */
	public static void printNum(int num[]) {
		for (int i = 0; i < num.length; i++) {
			System.out.print(num[i] + "\t");
		}
		System.out.println();
	}

	/**
	 * 随机指定范围内N个不重复的数 最简单最基本的方法
	 * 
	 * @param min
	 *            指定范围最小值
	 * @param max
	 *            指定范围最大值
	 * @param n
	 *            随机数个数
	 */
	public static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n - 1) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}
}
