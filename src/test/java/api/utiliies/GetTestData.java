package api.utiliies;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

public class GetTestData {
	static String FilePath;
	static String Sheet;

	public static String[][] GetData(String filePath, String sheetname) throws EncryptedDocumentException, IOException {
		FilePath = filePath;
		Sheet = sheetname;
		FileInputStream File = new FileInputStream(FilePath);
		Workbook wb = WorkbookFactory.create(File);
		Sheet sh = wb.getSheet(Sheet);
		int LastRowNum = sh.getLastRowNum();// number of rows in sheet
		int LastCellNum = sh.getRow(1).getLastCellNum();// number of cell in sheet
		String[][] TestData = new String[LastRowNum][LastCellNum];
		for (int i = 1; i <= LastRowNum; i++) {
			for (int j = 0; j <= LastCellNum - 1; j++) {
				Cell cellData = sh.getRow(i).getCell(j);
				CellType TypeOfData = sh.getRow(i).getCell(j).getCellType();
				switch (TypeOfData) {
				case STRING:
					TestData[i - 1][j] = cellData.getStringCellValue();
					break;

				/*case NUMERIC:
					TestData[i - 1][j] = cellData.getNumericCellValue();
					break;*/
				}
			}
			
			
		}
		wb.close();
		File.close();
		return TestData;
		
	}
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		String[][] TestData = GetData("C:\\\\Users\\\\shuah\\\\eclipse-RestAssured\\\\GorestAPITesting\\\\TestData\\\\TestData1.xlsx", "Sheet1");
		for(String[] data:TestData)
		{
			for(String d:data)
			{
				System.out.println(d);
			}
		}
	}
}
