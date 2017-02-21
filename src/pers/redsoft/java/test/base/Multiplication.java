package pers.redsoft.java.test.base;

/**
 * 3种循环嵌套--九九乘法表
 * 
 * @author redsoft
 *
 */
public class Multiplication {

	/**
	 * 主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("============for==================");
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j + "*" + i + "=" + j * i + "\t");
			}
			System.out.println();
		}
		System.out.println("=============while=================");
		int a = 1;
		while (a <= 9) {
			int b = 1;
			while (b <= a) {
				System.out.print(b + "*" + a + "=" + b * a + "\t");
				b++;
			}
			a++;
			System.out.println();
		}

		System.out.println("=============do while=================");
		int c = 1;
		do {
			int d = 1;
			do {
				System.out.print(d + "*" + c + "=" + d * c + "\t");
				d++;
			} while (d <= c);
			c++;
			System.out.println();
		} while (c <= 9);
	}
}
