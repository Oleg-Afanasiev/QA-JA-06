package com.academy.rozetka;

import com.academy.framework.BaseTest;
import com.academy.framework.util.PropertyReader;
import com.academy.rozetka.page.AuthFormPage;
import com.academy.rozetka.page.MainPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.Charset;

import static org.testng.Assert.assertEquals;

public class AllAuthTests extends BaseTest {
    private final static Logger LOG = LogManager.getLogger(com.academy.rozetka.AllAuthTests.class);

    protected String baseUrl = "https://rozetka.com.ua";

    @Test(dataProvider = "testFalseAuthDataProvider")
//  public void testSuccessAuth(String login, String password, String userNameExpected) {
    public void testFalseAuth(String login, String password, String errorMessageExpected1, String errorMessageExpected2) {
        driver.get(baseUrl);

        MainPage mainPage = new MainPage(driver);
        String oldMessage = mainPage.getEnterLinkText();
        AuthFormPage authFormPage = mainPage.clickEnterLink();

        authFormPage.enterLogin(login); // Вводим логин
        authFormPage.enterPassword(password); // Вводим пароль

        // Делаем unchecked чекбокса "Запомнить меня"
        authFormPage.clickRememberMeCheckBox();
//      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Напомнить пароль'])[1]/following::label[1]")).click();

//      mainPage = (MainPage)authFormPage.submit(true); // Нажимаем кнопку "Войти"
        authFormPage = (AuthFormPage) authFormPage.submit(false); // Нажимаем кнопку "Войти"

        // Click re-captcha
        authFormPage.clickReCaptcha();

        String loginFieldText = authFormPage.getLoginField().getAttribute("value");
        String passwordFieldText = authFormPage.getPasswordField().getAttribute("value");

        String backgroundColorLoginField = authFormPage.getLoginField().getCssValue("background-color"); // #ffd6d6 = rgb(255, 214, 214);
        String backgroundColorPasswordField = authFormPage.getPasswordField().getCssValue("background-color"); // #ffd6d6 = rgb(255, 214, 214);

        String borderColorLoginField = authFormPage.getLoginField().getCssValue("border-color");  // #fb3f4c = rgb(251, 63, 76);
        String borderColorPasswordField = authFormPage.getPasswordField().getCssValue("border-color");  // #fb3f4c = rgb(251, 63, 76);

//      authFormPage.testAuth(login, password, errorMessageExpected);

        // Введен неверный логин (без @) и неверный пароль
        // Введен неверный логин (без @) и верный пароль
        if (!loginFieldText.equals("") && !passwordFieldText.equals("")) { // Проверяем наличие текста в полях логина и пароля
            if (borderColorLoginField.equals(errorMessageExpected1) && !borderColorPasswordField.equals(errorMessageExpected1)) {
                LOG.debug("Введен неверный логин (без @) и неверный пароль");

                try {
                    assertEquals(backgroundColorLoginField, errorMessageExpected2);
                } catch (Error e) {
                    verificationErrors.append(e.toString());
                }
            }
        }

/*
        // Введен неверный логин (без @) и верный пароль
        if(!loginFieldText.equals("") && !passwordFieldText.equals("")) { // Проверяем наличие текста в полях логина и пароля
            if (borderColorLoginField.equals(errorMessageExpected1) && !borderColorPasswordField.equals(borderColorPasswordField)) {
                LOG.debug("Введен неверный логин (без @) и верный пароль");

                try {
                    assertEquals(backgroundColorLoginField, errorMessageExpected2);
                } catch (Error e) {
                    verificationErrors.append(e.toString());
                }
            }
        }
*/

        // Не введен логин и пароль
        if (loginFieldText.equals("") && passwordFieldText.equals("")) { // Проверяем наличие текста в полях логина и пароля
            if (borderColorLoginField.equals(errorMessageExpected1) && borderColorPasswordField.equals(errorMessageExpected1)) {
                LOG.debug("Не введен логин и пароль");

                try {
                    assertEquals(backgroundColorLoginField, errorMessageExpected2);
                } catch (Error e) {
                    verificationErrors.append(e.toString());
                }

                try {
                    assertEquals(backgroundColorPasswordField, errorMessageExpected2);
                } catch (Error e) {
                    verificationErrors.append(e.toString());
                }
            }
        }

        // Введен верный логин и не введен пароль
        if (!loginFieldText.equals("") && passwordFieldText.equals("")) { // Проверяем наличие текста в полях логина и пароля
            if (!borderColorLoginField.equals(errorMessageExpected1) && borderColorPasswordField.equals(errorMessageExpected1)) {
                LOG.debug("Введен верный логин и не введен пароль");

                try {
                    assertEquals(backgroundColorPasswordField, errorMessageExpected2);
                } catch (Error e) {
                    verificationErrors.append(e.toString());
                }
            }
        }

        // Не введен логин и введен верный пароль
        if (loginFieldText.equals("") && !passwordFieldText.equals("")) { // Проверяем наличие текста в полях логина и пароля
            if (borderColorLoginField.equals(errorMessageExpected1) && !borderColorPasswordField.equals(errorMessageExpected1)) {
                LOG.debug("Не введен логин и введен верный пароль");

                try {
                    assertEquals(backgroundColorLoginField, errorMessageExpected2);
                } catch (Error e) {
                    verificationErrors.append(e.toString());
                }
            }
        }

        // Введен верный логин и неверный пароль
        if (!loginFieldText.equals("") && !passwordFieldText.equals("")) { // Проверяем наличие текста в полях логина и пароля
            if (!borderColorLoginField.equals(errorMessageExpected1) && borderColorPasswordField.equals(errorMessageExpected1)) {
                LOG.debug("Введен верный логин и неверный пароль");

                WebElement errorPasswordElement = authFormPage.getErrorPasswordElement();

                // Проверяем сообщение об ошибке
                if (errorPasswordElement != null) {
                    String errorPasswordMessage = errorPasswordElement.getText(); // Сообщение об ошибке

                    if (!errorPasswordMessage.equals("")) {
                        try {
                            assertEquals(errorPasswordMessage, errorMessageExpected2);
                        } catch (Error e) {
                            verificationErrors.append(e.toString());
                        }
                    } else
                        LOG.debug("Отсутствует сообщение об ошибке!");
                } else
                    LOG.debug("Отсутствует WebElement с сообщением об ошибке!");
            }
        }

        // Введен неверный логин (с @) и верный пароль
        if (!loginFieldText.equals("") && !passwordFieldText.equals("")) { // Проверяем наличие текста в полях логина и пароля
            if (authFormPage.getLoginField().getText().contains("@")) {
                if (borderColorLoginField.equals(errorMessageExpected1) && !borderColorPasswordField.equals(errorMessageExpected1)) {
                    LOG.debug("Введен неверный логин (с @) и верный пароль");
/*
                    try {
                        assertEquals(backgroundColorLoginField, errorMessageExpected1);
                    } catch (Error e) {
                        verificationErrors.append(e.toString());
                    }
*/

                    WebElement errorLoginElement = authFormPage.getErrorLoginElement();

                    // Проверяем сообщение об ошибке
                    if (errorLoginElement != null) {
                        String errorLoginMessage = errorLoginElement.getText(); // Сообщение об ошибке

                        if (!errorLoginMessage.equals("")) {
                            try {
                                assertEquals(errorLoginMessage, errorMessageExpected2);
                            } catch (Error e) {
                                verificationErrors.append(e.toString());
                            }
                        } else
                            LOG.debug("Отсутствует сообщение об ошибке!");
                    } else
                        LOG.debug("Отсутствует WebElement с сообщением об ошибке!");
                }
            }
        }
    }

    @DataProvider(name = "testFalseAuthDataProvider")
    private Object[][] testFalseAuthDataProvider() {

        Object[][] authData = null;

/*
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./src/data/rozetka-data.xlsx"), Charset.forName("UTF-8")));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
*/
        File file = new File("./src/data/rozetka-data.xlsx");

        try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheet("AuthDataRozetka");
            authData = new Object[sheet.getLastRowNum()][4];

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                String email = "";
                String password = "";
                String errorMessage2 = "";

                if (row.getCell(0) != null)
                    email = row.getCell(0).getStringCellValue();
                if (row.getCell(1) != null)
                    password = row.getCell(1).getStringCellValue();

                String errorMessage1 = row.getCell(2).getStringCellValue();

                if (row.getCell(3) != null)
                    errorMessage2 = row.getCell(3).getStringCellValue();

                authData[i - 1][0] = email;
                authData[i - 1][1] = password;
                authData[i - 1][2] = errorMessage1;
                authData[i - 1][3] = errorMessage2;
            }
        } catch (IOException | InvalidFormatException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return authData;
    }
}








