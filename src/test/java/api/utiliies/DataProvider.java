package api.utiliies;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;

public class DataProvider {
	String FilePath;
@org.testng.annotations.DataProvider(name="Data")
public String[][] getdata() throws EncryptedDocumentException, IOException
{	FilePath="C:\\Users\\shuah\\eclipse-RestAssured\\GorestAPITesting\\TestData\\TestData1.xlsx";
	String[][] TD = GetTestData.GetData(FilePath, "Sheet1");
	return TD;
}
}
