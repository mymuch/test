
package pers.redsoft.java.test.base;

/**
 * 大写英文字符转换
 * 
 * @author redsoft
 *
 */
public class UpperToLower {

	/**
	 * 主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 验证字母a到A，z到Z的规律
		printCharCode();
		System.out.println("================分割线===============");
		// 由上面的方法得知字母a到A，z到Z的规律为相差32，对下面大小写字符串进行全部小写或者全部大写的转换。
		String str = "Hello World !!";
		// 转为char数组
		char[] tolower = str.toCharArray();
		// 先拷贝一份char数组用于存放相同长度的字符
		char[] toupper = tolower.clone();
		// 循环字符串的字符
		for (int i = 0; i < tolower.length; i++) {
			// 每一个字符的值
			char point = tolower[i];
			// 判断是否是大写的字母，在90和65之间
			if (point <= 90 && point >= 65) {
				// 就转换为小写(加32)
				tolower[i] = (char) (point + 32);
			}
			// 判断是否是小写的字母，在97和122之间
			if (point <= 122 && point >= 97) {
				// 就转换为大写(減32)
				toupper[i] = (char) (point - 32);
			}
		}
		// 小写的char数组转换为string字符串
		String strlower = new String(tolower);
		// 大写的char数组转换为string字符串
		String strupper = new String(toupper);
		// 打印小写字符串
		System.out.println(strlower);
		// 打印大写字符串
		System.out.println(strupper);
	}

	/**
	 * 验证字母a到A，z到Z的规律
	 */
	static void printCharCode() {
		// 为了测定字母a到A，z到Z的规律，定义这四个字符。
		String aazz = "aAzZ";
		// 将字符串转为char数组
		char[] charaz = aazz.toCharArray();
		// 分拆这四个字符
		for (int i = 0; i < charaz.length; i++) {
			char chars = charaz[i];
			System.out.println(chars + " = " + (int) chars);
		}
		// 得出结论a~A相差32，z~Z相差32
		System.out.println("a~A = " + ((int) charaz[0] - (int) charaz[1]));
		System.out.println("z~Z = " + ((int) charaz[2] - (int) charaz[3]));
	}

}