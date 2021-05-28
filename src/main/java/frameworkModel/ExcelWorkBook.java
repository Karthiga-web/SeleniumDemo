package frameworkModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import config.Constants;

public class ExcelWorkBook {
	static double count = 0;

	public void putCartData(List<Double> quantity, List<Double> price, String total) {
		File file = new File("C:\\Users\\grkar\\eclipse-workspace\\seleniumdemo\\CartData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("TestData");
		if (sheet.getRow(0) == null) {
			sheet.createRow(0).createCell(0).setCellValue("Quantity");
			sheet.getRow(0).createCell(1).setCellValue("Price");
		} else {
			sheet.getRow(0).createCell(0).setCellValue("Quantity");
			sheet.getRow(0).createCell(1).setCellValue("Price");
		}
		System.out.println(sheet.getLastRowNum());
		Iterator<Row> rows = sheet.iterator();
		for (int i = 0; i < price.size(); i++) {
			System.out.println(sheet.getLastRowNum());
			if (sheet.getRow(sheet.getLastRowNum() + 1) == null) {
				sheet.createRow(sheet.getLastRowNum() + 1).createCell(0).setCellValue(price.get(i));
				sheet.getRow(sheet.getLastRowNum()).createCell(1).setCellValue(quantity.get(i));
			} else {
				sheet.getRow(sheet.getLastRowNum() + 1).createCell(0).setCellValue(price.get(i));
				sheet.getRow(sheet.getLastRowNum()).createCell(1).setCellValue(quantity.get(i));
			}
		}

		if (sheet.getRow(sheet.getLastRowNum() + 1) == null) {
			sheet.createRow(sheet.getLastRowNum() + 1).createCell(0).setCellValue("Total");
			sheet.getRow(sheet.getLastRowNum()).createCell(1).setCellValue(total);
		} else {
			sheet.getRow(sheet.getLastRowNum() + 1).createCell(0).setCellValue("Total");
			sheet.getRow(sheet.getLastRowNum()).createCell(1).setCellValue(total);
		}

		try {
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Over");
	}

	public String getData(String uiData) throws IOException {
		// fileInputStream argument
		String a;
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\grkar\\eclipse-workspace\\seleniumdemo\\ExcelDriven.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
//				int sheets = workbook.getNumberOfSheets();
//				for (int i = 0; i < sheets; i++) {
//					if (workbook.getSheetName(i).equalsIgnoreCase("testdata")) {
		XSSFSheet sheet = workbook.getSheet("UIData");
		// Identify Testcases column by scanning the entire 1st row
		Iterator<Row> rows = sheet.iterator();// sheet is collection of rows
		Row firstrow = rows.next();
		Iterator<Cell> ce = firstrow.cellIterator();// row is collection of cells
		int k = 0;
		int column = 0;
		while (ce.hasNext()) {
			Cell value = ce.next();
			if (value.getStringCellValue().equalsIgnoreCase("Keyword")) {
				column = k;
			}
			k++;
		}
//						System.out.println(column);
		int rowNumber = 0;
		//// once coloumn is identified then scan entire testcase coloum to identify
		//// purchase testcase row
		while (rows.hasNext()) {
			Row r = rows.next();
			if (r.getCell(column).getStringCellValue().equalsIgnoreCase(uiData)) {
				//// after you grab purchase testcase row = pull all the data of that row and
				//// feed into test
//								rowNumber = r.getRowNum();
				Iterator<Cell> cv = r.cellIterator();
				cv.next();
				while (cv.hasNext()) {
					Cell cell = cv.next();
					switch (cell.getCellType()) {
					case BOOLEAN:
						return String.valueOf(cell.getBooleanCellValue());
					case NUMERIC:
						return String.valueOf((int) cell.getNumericCellValue());
					case ERROR:
						return String.valueOf(cell.getErrorCellValue());
					case STRING:
						return cell.getStringCellValue();
					case BLANK:
					case FORMULA:
					default:
						return "";
					}
				}
			}
		}
//					}
//				}
		return "";
	}

}
