package pers.redsoft.java.test.base;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author redsoft
 *
 */
public class CreateCalendarForXlsx {

	/**
	 * excel的文件名
	 */
	private final static String FILENAME = "CalendarForXlsx.xlsx";

	/**
	 * excel的行index
	 */
	private static int rows;

	/**
	 * excel的book对象
	 */
	private static Workbook wb;

	/**
	 * 用于指定excel的A列到G列
	 */
	private final static String AG = "ABCDEFG";

	/**
	 * 用于输出title的周一到周日
	 */
	private final static String[] WEEKEND = { "日", "一", "二", "三", "四", "五", "六" };

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// ####################### 窗体设置 ##########################
		// 生成带title的JFrame窗体
		JFrame jf = new JFrame("excel中创建日历");
		// 设置叉按钮为退出动作
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗体的左边距，上边距，宽度，高度
		jf.setBounds(0, 0, 500, 100);
		// 设置窗体大小不可调整
		jf.setResizable(false);

		// ####################### 面板设置 ##########################
		// 设置JPanel面板
		JPanel jpanel = new JPanel();
		// 设置面板的布局为null
		jpanel.setLayout(null);
		// 将面板添加到窗体
		jf.add(jpanel);

		// ####################### 年份输入框 ##########################
		// 生成label标签
		JLabel jlabelyear = new JLabel("指定年份:");
		// 设置label标签的位置
		jlabelyear.setBounds(10, 10, 80, 25);
		// 将label标签添加到面板
		jpanel.add(jlabelyear);

		// 生成text控件用于输入年份
		JTextField jtext = new JTextField();
		// 设置默认内容为系统日期的年
		jtext.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		// 对输入的内容进行键盘监听
		jtext.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// 如果输入的内容不为数字，删除键，回车键
				if (!Character.isDigit(arg0.getKeyChar()) && arg0.getKeyChar() != '\b' && arg0.getKeyChar() != '\n') {
					// 消除当前键入的内容
					arg0.consume();
					// 弹出错误信息框
					JOptionPane.showMessageDialog(null, "请输入整数", "友情提示", JOptionPane.ERROR_MESSAGE);
				}
				// 如果输入的内容为数字，并且长度大于4
				if (Character.isDigit(arg0.getKeyChar()) && jtext.getText().length() + 1 > 4) {
					// 消除当前键入的内容
					arg0.consume();
					// 弹出错误信息框
					JOptionPane.showMessageDialog(null, "请输入4位以内整数", "友情提示", JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		// 设置输入框的位置
		jtext.setBounds(90, 10, 50, 25);
		// 将text控件添加到面板中
		jpanel.add(jtext);

		// ####################### 指定excel出力目录 ##########################
		// 生成按钮控件用于指定excel出力的目录
		JButton jFileButton = new JButton("指定excel出力目录");
		// 设置按钮的位置
		jFileButton.setBounds(200, 10, 160, 25);
		// 将按钮添加到面板中
		jpanel.add(jFileButton);

		// 生成label控件
		JLabel jlabel = new JLabel("出力Excel目录:");
		// 设置label的位置
		jlabel.setBounds(10, 40, 100, 25);
		// 将label添加到面板中
		jpanel.add(jlabel);

		// 生成label控件用于显示指定的出力目录
		JLabel jlabelPath = new JLabel();
		// 设置label的位置
		jlabelPath.setBounds(110, 40, 400, 25);
		// 将label添加到面板中
		jpanel.add(jlabelPath);
		// 指定目录按钮按下后的事件监听
		jFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// 生成文件选择器控件
				JFileChooser jfile = new JFileChooser();
				// 设置文件选择器为只选择目录模式
				jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				// 显示文件选择器
				jfile.showOpenDialog(jpanel);
				// 如果选了目录
				if (jfile.getSelectedFile() != null) {
					// label控件则显示选中的目录内容
					jlabelPath.setText(jfile.getSelectedFile().getPath());
				}
			}
		});

		// ####################### 执行按钮 ##########################
		// 生成执行按钮
		JButton jExcuteButton = new JButton("执行！");
		// 设置按钮的位置
		jExcuteButton.setBounds(400, 10, 80, 25);
		// 对按钮进行事件监听
		jExcuteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// 如果没有输入年份
				if ("".equals(jtext.getText()) || jtext.getText().length() > 4) {
					// 弹出错误信息框
					JOptionPane.showMessageDialog(null, "请输入4位以内年份", "友情提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// 取得输入的年份内容
				String inputStr = jtext.getText();
				// 转换为char数组，分解每一个输入的值
				char[] numcheck = inputStr.toCharArray();
				// 循环输入的每一个字符
				for (int i = 0; i < numcheck.length; i++) {
					// 把输入的字符转换为codepoint
					int numPoint = (int) numcheck[i];
					// 如果codepoint不在整数0~9的范围内
					if (numPoint < 48 || numPoint > 57) {
						// 弹出错误信息框
						JOptionPane.showMessageDialog(null, "年份请输入整数", "友情提示", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				// 如果没有选择目录
				if ("".equals(jlabelPath.getText())) {
					// 弹出错误信息框
					JOptionPane.showMessageDialog(null, "请指定excel出力目录", "友情提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					// 往excel文件中输出日历
					writeToExcelFile(jlabelPath.getText(), Integer.valueOf(jtext.getText()));
				} catch (FileNotFoundException e) {
					// 弹出错误信息框
					JOptionPane.showMessageDialog(null, "文件创建失败！\n" + e.getMessage(), "友情提示",
							JOptionPane.ERROR_MESSAGE);
					return;
				} catch (IOException e) {
					// 弹出错误信息框
					JOptionPane.showMessageDialog(null, "文件写入失败!\n" + e.getMessage(), "友情提示",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				// 输出成功后弹出信息框
				int result = JOptionPane.showConfirmDialog(null,
						"Excel作成日历成功！\n是否立即打开下面的文件?\n" + jlabelPath.getText() + "\\" + FILENAME, "确认信息",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					File file = new File(jlabelPath.getText() + "\\" + FILENAME);
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e) {
						// 弹出错误信息框
						JOptionPane.showMessageDialog(null, "打开文件失败!\n" + e.getMessage(), "友情提示",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		// 将执行按钮添加到面板
		jpanel.add(jExcuteButton);

		// ####################### 窗体居中设置 ##########################
		// 获取屏幕的尺寸
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口居中显示
		jf.setLocation(screenSize.width / 2 - jf.getWidth() / 2, screenSize.height / 2 - jf.getHeight() / 2);
		// 显示窗体
		jf.setVisible(true);
	}

	/**
	 * 生成Excel文件
	 * 
	 * @param path
	 *            生成的目录path
	 * @param year
	 *            输入的年份
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	/**
	 * @param path
	 * @param year
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void writeToExcelFile(String path, int year) throws FileNotFoundException, IOException {
		// 初始化行数
		rows = 0;
		// 创建Calendar实例
		Calendar c = Calendar.getInstance();
		// 系统当日日期
		Calendar now = (Calendar) c.clone();
		// 创建xlsx格式的book
		wb = new XSSFWorkbook();
		// 创建sheet，命名为例"Calendar2016"
		Sheet sheet = wb.createSheet("Calendar" + year);
		// 关闭网格设置
		sheet.setDisplayGridlines(false);
		// 调整页面为一页打印
		sheet.setFitToPage(true);
		// 设置整个sheet的列宽
		sheet.setDefaultColumnWidth(2);

		// 设置输入年的月日为1月1日。
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
		// 复制一份当前的年月日
		Calendar ccopy = (Calendar) c.clone();
		// 循环处理输入年的每一天
		while (c.get(Calendar.YEAR) == year) {
			// 用于判断是否为今日
			boolean today = false;
			// 如果是系统时间，设定位true
			if (now.compareTo(c) == 0) {
				today = true;
			}
			// 获取当前月
			int month = c.get(Calendar.MONTH);
			// 获取当月的日
			int mday = c.get(Calendar.DAY_OF_MONTH);
			// 获取当日是星期几
			int wday = c.get(Calendar.DAY_OF_WEEK);
			// 如果是当月的第一天
			if (mday == 1) {
				// 输出title行
				writeTitle(month, sheet);
				// excel的行数+1
				rows++;
				// 判断当月的第一天在一周的位置，把之前的位置设置成空白
				for (int i = 1; i < wday; i++) {
					writeDate(i, null, sheet, false);
				}
			}
			// 输出当天的日
			writeDate(wday, Double.valueOf(mday), sheet, today);

			// 设定下一天
			ccopy.add(Calendar.DAY_OF_YEAR, 1);
			// 如果该天是周六，或者下一天不是当前月，则换行
			if (wday == 7 || ccopy.get(Calendar.MONTH) != month) {
				for (int i = 0; i < AG.length(); i++) {
					setRegionBorder(BorderStyle.THIN.getCode(),
							CellRangeAddress.valueOf("$" + AG.charAt(i) + "$" + (rows + 1)), sheet);
				}
				rows++;
			}
			// 准备处理下一天
			c.add(Calendar.DAY_OF_YEAR, 1);
		}
		// 创建输出流文件
		FileOutputStream fos = new FileOutputStream(path + "/" + FILENAME);
		// 把book写入到该文件
		wb.write(fos);
		// 关闭book对象
		wb.close();
		// 关闭流文件
		fos.close();
	}

	/**
	 * 作成title
	 * 
	 * @param month
	 * @param sheet
	 */
	private static void writeTitle(int month, Sheet sheet) {
		// 创建行
		Row rowMonth = sheet.createRow(rows);
		// 输出当前的月份
		Cell celMonth = rowMonth.createCell(0);
		celMonth.setCellValue(month + 1 + "月");
		// 生成字体对象
		Font font = wb.createFont();
		// 设置字体名称
		font.setFontName("SimSun");
		// 设置字体大小
		font.setFontHeightInPoints((short) 25);
		// 设置粗体
		font.setBold(true);
		// 创建单元格格式
		CellStyle cellStyle = wb.createCellStyle();
		// 设置居中对齐
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直居中对齐
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置单元格的字体
		cellStyle.setFont(font);
		// 设置单元格的格式
		celMonth.setCellStyle(cellStyle);
		// 合并7列单元格
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rows + 1) + ":$G$" + (rows + 1)));
		// 设置单元格边框为实线
		setRegionBorder(BorderStyle.THIN.getCode(), CellRangeAddress.valueOf("$A$" + (rows + 1) + ":$G$" + (rows + 1)),
				sheet);

		// 换行
		rows++;
		// 创建行
		Row row = sheet.createRow(rows);
		// 创建单元格格式
		CellStyle cs = wb.createCellStyle();
		// 设置填充背景色为蓝色
		cs.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		// 设置填充
		cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// 生成font
		Font ft = wb.createFont();
		// 设置白色字体
		ft.setColor(IndexedColors.WHITE.getIndex());
		// 将字体添加到单元格格式
		cs.setFont(ft);
		for (int i = 0; i < 7; i++) {
			// 创建单元格
			Cell cell = row.createCell(i);
			// 设定单元格的内容为"日"等
			cell.setCellValue(WEEKEND[i]);
			// 设定单元格格式
			cell.setCellStyle(cs);
		}
		// 设置单元格边框为实线
		for (int i = 0; i < AG.length(); i++) {
			setRegionBorder(BorderStyle.THIN.getCode(), CellRangeAddress.valueOf("$" + AG.charAt(i) + "$" + (rows + 1)),
					sheet);
		}
	}

	/**
	 * 写入日期
	 * 
	 * @param wday
	 *            周几
	 * @param date
	 *            日期
	 * @param sheet
	 *            当前sheet
	 * @param today
	 *            是否为今日
	 */
	private static void writeDate(int wday, Double date, Sheet sheet, boolean today) {
		// 获取指定行的内容
		Row row = sheet.getRow(rows);
		// 如果获取不到内容
		if (row == null) {
			// 创建行
			row = sheet.createRow(rows);
		}
		// 创建指定单元格
		Cell cell = row.createCell(wday - 1);
		// 创建单元格格式
		CellStyle cs = wb.createCellStyle();
		// 如果是周六或者周日并且date不为空
		if ((wday == 1 || wday == 7) && date != null) {
			// 设置填充背景色为黄色
			cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			// 设置填充
			cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		// 如果是今日，则对于该单元格进行标红色的设定。
		if (today) {
			// 设置填充背景色为红色
			cs.setFillForegroundColor(IndexedColors.RED.getIndex());
			// 设置填充
			cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		// 设置单元格的格式
		cell.setCellStyle(cs);
		// 输入当日到单元格
		if (date == null) {
			cell.setCellValue(" ");
		} else {
			cell.setCellValue(date);
		}
	}

	/**
	 * 设定指定范围内单元格的边框线
	 * 
	 * @param border
	 *            边框线的样式
	 * @param region
	 *            单元格范围
	 * @param sheet
	 *            sheet对象
	 */
	private static void setRegionBorder(short border, CellRangeAddress region, Sheet sheet) {
		//设置上部边框线
		RegionUtil.setBorderTop(border, region, sheet);
		//设置下部边框线
		RegionUtil.setBorderBottom(border, region, sheet);
		//设置左边边框线
		RegionUtil.setBorderLeft(border, region, sheet);
		//设置右边边框线
		RegionUtil.setBorderRight(border, region, sheet);
	}
}
