package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

abstract class AbstractTestsForCRM extends AbstractTests {

    @BeforeEach
    void initMainPage(){
        String url = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login",
               errMsg = "Страница не доступна";
        Assertions.assertDoesNotThrow(()-> getDriver().navigate().to(url), errMsg);
        Assertions.assertTrue(true);
    }
}
