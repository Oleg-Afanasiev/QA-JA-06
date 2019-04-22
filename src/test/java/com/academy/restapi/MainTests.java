package com.academy.restapi;

import com.academy.framework.BaseTest;
import com.academy.restapi.page.AddPage;
import com.academy.restapi.page.HomePage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;

public class MainTests extends BaseTest {

    protected String baseUrl = "http://localhost:8081/subscribers";

    // Добавление пользователей в базу данных через веб-интерфейс
    @Test(dataProvider="addSubscribersProvider")
     public void testAddSubscribers(String firstName, String lastName, char gender, String age){
        driver.get(baseUrl);

        HomePage homePage = new HomePage(driver);
        AddPage addPage = homePage.addSubscriber();

        addPage.enterFirstName(firstName);
        addPage.enterLastName(lastName);

        try {
            addPage.selectGender(gender);
        }
        catch(Exception e){

        }

        addPage.enterAge(age);

        addPage.submit();
    }

    @DataProvider(name="addSubscribersProvider")
    private Object[][] addSubscribersProvider() throws IOException {

        String pathData = "./test-data/mobile.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( pathData ) );
        XSSFSheet sheet = workbook.getSheet( "Subscribers" );

        Object[][] testData = new Object[sheet.getLastRowNum()][4];
        System.out.println(sheet.getLastRowNum());
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow parRow = sheet.getRow(i);
            testData[i-1][0] = parRow.getCell(0).getStringCellValue();
            testData[i-1][1] = parRow.getCell(1).getStringCellValue();
            testData[i-1][2] = parRow.getCell(2).getStringCellValue().charAt(0);

            String age = String.valueOf(parRow.getCell(3).getNumericCellValue());
            int index = age.indexOf(".");
            age = age.substring(0, index);

            testData[i-1][3] = age;
        }
        return testData;
    }
}
