package org.example.TestForCRM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddAccountInCRM extends AddUserInCRM {

    public AddAccountInCRM(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[contains(.,'Open Account')]")
    private WebElement openAccount;
    @FindBy(xpath = "//select[@id=\"userSelect\"]/option[contains(.,'Ivan Ivanov')]")
    private WebElement selectUser;
    @FindBy(xpath = "//*[@id=\"currency\"]/option[contains(.,'Dollar')]")
    private WebElement selectCurrency;
    @FindBy(xpath = "//form/button[contains(.,'Process')]")
    private WebElement clickBtnProcess;

    @Override
    public AddAccountInCRM openAccount() {
        this.openAccount.click();
        return this;
    }

    @Override
    public AddAccountInCRM selectUser() {
        this.selectUser.click();
        return this;
    }

    @Override
    public AddAccountInCRM selectCurrency() {
        this.selectCurrency.click();
        return this;
    }

    @Override
    public AddAccountInCRM clickBtnProcess() {
        this.clickBtnProcess.click();
        return this;
    }
}
