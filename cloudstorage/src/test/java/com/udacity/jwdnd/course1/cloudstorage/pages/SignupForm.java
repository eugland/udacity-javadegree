package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupForm {
    @FindBy(id="inputFirstName")
    private WebElement firstnameField;

    @FindBy(id="inputLastName")
    private WebElement lastnameField;

    @FindBy(id="inputUsername")
    private WebElement usernameField;

    @FindBy(id="inputPassword")
    private WebElement passwordField;

    @FindBy(id="result")
    private WebElement result;

    public SignupForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setFirstname(String firstname) {
        firstnameField.sendKeys(firstname);
    }

    public void setLastname(String lastname) {
        lastnameField.sendKeys(lastname);
    }

    public void setUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void clearUsername() {
        usernameField.clear();
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public String getResult() {
        return result.getText();
    }

    public void submit(){
        usernameField.submit();
    }
}
