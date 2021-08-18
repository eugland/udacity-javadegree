package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.apache.ibatis.javassist.bytecode.ExceptionTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Optional;

public class LoginForm {
    @FindBy(id="inputUsername")
    private WebElement usernameField;

    @FindBy(id="inputPassword")
    private WebElement inputPasswordField;


    @FindBy(id="loginSubmitButton")
    private WebElement submitButton;

    @FindBy(id = "loginFormContainer")
    private WebElement formContainer;

//    @FindBy(id="error-msg")
//    private WebElement mError;

//    @FindBy(id="login-form-contaienr")
//    private WebElement mFormContainer;
//
//    @FindBy(id="login-submit-button")
//    private WebElement mSubmitButton;


    public LoginForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setUsernameField(String usernameField) {
        this.usernameField.sendKeys(usernameField);
    }

    public void setPassWord(String password) {
        inputPasswordField.sendKeys(password);
    }

//    public String getErrorMessage() {
//        System.out.println(mError);
//        return mError.getText();
//    }

    // Originally using username.submit, but somehow goes to sign up page
    public void submit() {
        submitButton.click();
    }

    public Optional<String> getErrorMessage() {
        if (formContainer != null) {
            try {
                WebElement errorField = formContainer.findElement(By.id("error-msg"));
                return Optional.ofNullable(errorField.getText());
            } catch (Exception e) {}
        }
        return Optional.empty();
    }
}
