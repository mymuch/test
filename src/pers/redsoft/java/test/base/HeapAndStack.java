package pers.redsoft.java.test.base;

/**
 * JAVA中的内存堆栈
 * 
 * @author redsoft
 *
 */
public class HeapAndStack {

	/**
	 * main主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建a变量于栈中，并且在堆里创建Person对象，a变量指向这个对象，通过构造方法传递Person的name属性为jack
		Person a = new Person("jack");
		// 创建b变量于栈中，指向a变量所引用的存在于堆里的name为jack的对象。
		Person b = a;
		// 把堆里对象的name属性更改为mark
		b.name = "mark";
		// 打印a变量的name，因为a和b共享一个引用对象，所以a的name为mark
		a.display();
		// 打印b变量的name，因为a和b共享一个引用对象，所以b的name为mark
		b.display();
		// 内存地址比较
		System.out.println(a.name == b.name);

		System.out.println("==================分割线=====================");
		// 在栈里定义一个变量aa，用new在堆里创建一个字符串iPhone7，iphone7这个值存放在常量池中。关系为栈变量指向堆对象，堆对象里的值引用了常量池的iphone7。
		String aa = new String("iphone7");
		// 在栈里定义一个变量bb，并且指向aa所引用的堆里的对象
		String bb = aa;
		// 因为bb变量和aa变量目前都同时指向了同一个堆里的对象，所以内存地址相同。
		System.out.println(aa == bb);
		// 在栈里 的变量bb此时直接引用了一个常量池中的值iphone7Plus，这时，原来的bb指向的aa引用对象依然存在，只不过跟bb没有关系了。
		// 【打个比喻：aa商家租了一个门面用于存放iphone7，而这个门面里的iphone7是直接从苹果公司拿到的，
		// bb商家本来可以借用这个门面拿货，但是bb商家认识到高昂的门面租金（内存容量）是种铺张浪费，所以bb申请直接从苹果公司拿货7plus。】
		bb = "iphone7Plus";
		// aa只是单纯地发现了bb商家进了更高级的7plus，于是又开辟了一个空间用于存放7plus，那么此时iphone7的仓库因为没有人用了，被工商部门没收了（GC回收）。
		aa = new String("iphone7Plus");
		// 打印变量aa和bb的值，打印值都是常量池中的iphone7Plus
		System.out.println("aa变量 = " + aa + "\nbb变量 = " + bb);
		// 因为aa变量指向的对象一直存在于堆内，而bb变量是直接引用了常量池。所以内存地址不同。
		System.out.println(aa == bb);
		// 经过一段时间，aa商家终于明白了为什么bb赚的钱多，因为bb不需要仓库，由苹果公司代为发货。所以aa决定也直接申请从苹果公司拿货7plus。
		aa = "iphone7Plus";
		// 因为aa和bb商家都是从苹果公司拿货的（常量池），他们的内存地址相同
		System.out.println(aa == bb);
	}

}

/**
 * 定义一个Person类
 * 
 * @author redsoft
 *
 */
class Person {
	// Person类唯一的属性：name
	String name;

	/**
	 * Person构造方法
	 */
	Person(String name) {
		this.name = name;
	}

	/**
	 * 显示属性name方法
	 */
	public void display() {
		// 打印属性name的值
		System.out.println("Name = " + this.name);
	}
}
