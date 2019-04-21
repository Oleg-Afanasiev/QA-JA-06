package com.academy.rozetka.page;

import com.academy.framework.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AuthFormPage extends BasePage {

    @FindBy(id = "auth_email")
    private WebElement loginField;

    @FindBy(id = "auth_pass")
    private WebElement passwordField;

    // CheckBox 'Remember Me'
    @FindBy(id = "remember_me")
    private WebElement rememberMeForAttribute;

    // CheckBox 'Remember Me'
    @FindBy(xpath = "(.//*[normalize-space(text()) and normalize-space(.)='Напомнить пароль'])[1]/following::label[1]")
    private WebElement rememberMeForClick;

    // Re-captcha
    @FindBy(className = "recaptcha-checkbox-checkmark")
    private List<WebElement> recaptcha;

    @FindBy(className = "auth-modal__form-email")
    private List<WebElement> errorLoginElement;

    @FindBy(className = "error-message_color_red")
    private List<WebElement> errorPasswordElement;

    @FindBy(css = "body > app-root > div > div:nth-child(2) > div.app-rz-common > auth-modal > modal-window > div > div > div > auth-content > div > form > div > button")
    private WebElement submitButton;

    public AuthFormPage(WebDriver driver) {
        super(driver);
    }

    // Вводим логин
    public AuthFormPage enterLogin(String login) {
//        loginField.click();
//        loginField.clear();
//        loginField.sendKeys(login);
        enterText(loginField, login);
        return this;
    }

    // Вводим пароль
    public AuthFormPage enterPassword(String password) {
//        passwordField.click();
//        passwordField.clear();
//        passwordField.sendKeys(password);
        enterText(passwordField, password);
        return this;
    }

    // Щелкаем по чекбоксу "Remember Me"
    public AuthFormPage clickRememberMeCheckBox() {

        String isChecked = rememberMeForAttribute.getAttribute("checked");

        // Делаем unchecked
//      if(rememberMe.isSelected())
        if (isChecked.equals("true"))
            clickWebElement(rememberMeForClick);
        return this;
    }

    // Re-captcha
    public AuthFormPage clickReCaptcha() {
        try {
            waitUntilElementIsVisible(10, recaptcha.get(0));
            clickWebElement(recaptcha.get(0));
            return this;
/*
            String isChecked = recaptcha.get(0).getAttribute("checked");
            if (isChecked.equals("false"))
                clickWebElement(recaptcha.get(0));
            return this;
*/
        } catch (Exception e) {
            return null;
        }

/*
        if (!recaptcha.isEmpty()) {
            String isChecked = recaptcha.get(0).getAttribute("checked");
            if (isChecked.equals("false"))
                clickWebElement(recaptcha.get(0));
        }
        return this;
*/
    }

    // Щелкаем на кнопку Submit
    public BasePage submit(boolean isCorrect) {
        clickWebElement(submitButton);
        if (isCorrect)
            return new MainPage(driver);
        else
            return this;
    }

/*
    public AuthFormPage testAuth(String login, String password, String errorMessageExpected){

        return this;
    }
*/

    public WebElement getLoginField() {
        return loginField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getErrorLoginElement() {
        try {
            waitUntilElementIsVisible(10, errorLoginElement.get(0));
            return errorLoginElement.get(0);
        } catch (Exception e) {
            return null;
        }

/*
        if(!errorLoginElement.isEmpty()) {
            return errorLoginElement.get(0);
        }
        else
            return null;
*/
    }

    public WebElement getErrorPasswordElement() {
        try {
            waitUntilElementIsVisible(10, errorPasswordElement.get(0));
            return errorPasswordElement.get(0);
        } catch (Exception e) {
            return null;
        }
/*
        if(!errorPasswordElement.isEmpty()){
            return errorPasswordElement.get(0);
        }
        else
            return  null;
*/
    }
}


