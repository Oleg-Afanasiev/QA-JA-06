package com.academy.restapi.page;

import com.academy.framework.BasePage;
import org.apache.commons.math3.analysis.function.Add;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddPage extends BasePage {

    @FindBy(id="fname")
    private WebElement fname;

    @FindBy(id="lname")
    private WebElement lname;

    @FindBy(id="MALE")
    private WebElement male;

    @FindBy(id="FEMALE")
    private WebElement female;

    @FindBy(id="age")
    private WebElement age;

    @FindBy(xpath = "/html/body/div/form/button")
    private WebElement submit;

    public AddPage(WebDriver driver){
        super(driver);
    }

    //==================================================//

    public AddPage enterFirstName(String firstName){
        enterText(fname, firstName);
        return this;
    }

    public AddPage enterLastName(String lastName){
        enterText(lname, lastName);
        return this;
    }

    public AddPage selectGender(char gender)throws Exception{
        if(gender == 'm')
            male.click();
        if(gender == 'f')
            female.click();
        else
            throw new Exception("Input right data!");

        return this;
    }

    public AddPage enterAge(String ageSubscriber){
        enterText(age, ageSubscriber);
        return this;
    }

    public HomePage submit(){
        submit.click();
        return new HomePage(driver);
    }
}

