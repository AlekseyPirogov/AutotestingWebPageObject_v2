package org.example;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AbstractPage {

    private WebDriver driver;

    public AbstractPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver(){
        return this.driver;
    }

    // Метод для кликов по всплывающему окну с алертом JavaScript
    public void actionAcceptJavaScript() throws InterruptedException{
        try {
            Thread.sleep(1000);     // <-- задержка
            getDriver().switchTo().alert().accept();     // <-- соглашаемся с alert на странице
        } catch (NoAlertPresentException e) {
            System.out.println("Сайт не выдал сообщения с подтверждением");
            e.getMessage();
        }
    }

}
