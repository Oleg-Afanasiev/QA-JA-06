
package com.academy.restapi.page;

import com.academy.framework.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Factory;

public class HomePage extends BasePage {

    @FindBy(id = "add")
    private WebElement add; // Кнопка add

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public AddPage addSubscriber() {
        add.click();
        return new AddPage(driver);
    }
}

