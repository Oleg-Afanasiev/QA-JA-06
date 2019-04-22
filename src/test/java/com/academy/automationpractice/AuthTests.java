package com.academy.automationpractice;

import com.academy.automationpractice.model.AuthData;
import com.academy.framework.BaseTest;
import com.academy.framework.util.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class AuthTests extends BaseTest {

    private final static Logger LOG = LogManager.getLogger(AuthTests.class);

    protected String errorMessageCssLocator = "#center_column > div.alert.alert-danger > ol > li";
    protected String baseUrl = "http://automationpractice.com/index.php";

    @Test(dataProvider = "authDataProvider")
    public void testAuthFail(String login, String password, String expectedMessage) throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Sign in")).click();

        // Заполняем форму логин/пароль
        WebElement loginField = driver.findElement(By.id("email"));
        loginField.click();
        loginField.clear();
        loginField.sendKeys(login);

        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.id("SubmitLogin")).click();

        WebElement webElementWithErrorMessage = driver.findElement(By.cssSelector(errorMessageCssLocator));
        String actualErrorMessage = webElementWithErrorMessage.getText();
        try {
            assertEquals(actualErrorMessage, expectedMessage);
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    private List<AuthData> readTestData() {
        List<AuthData> testDataList = new ArrayList<>();

        // Прочитать из excel файла
        return testDataList;
    }

    // TODO move path to property
    @DataProvider(name="authDataProvider")
    public Object[][] authDataProvider() throws IOException {
        String pathData = PropertyReader.from("automationpractice").getProperty("auth.incorrect.data");

        System.out.println(Paths.get(pathData).toAbsolutePath());
        XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( pathData ) );
        XSSFSheet sheet = workbook.getSheet( "Sheet1" );
        Object[][] testData = new Object[sheet.getLastRowNum()][3];
        System.out.println(sheet.getLastRowNum());
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            XSSFRow parRow = sheet.getRow(i);
            testData[i-1][0] = (parRow.getCell(0) == null) ? "" : parRow.getCell(0).getStringCellValue();
            testData[i-1][1] = (parRow.getCell(1) == null) ? "" : parRow.getCell(1).getStringCellValue();
            testData[i-1][2] = parRow.getCell(2).getStringCellValue();
        }
        return testData;
    }
}
