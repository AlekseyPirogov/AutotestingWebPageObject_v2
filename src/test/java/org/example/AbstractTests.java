package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public abstract class AbstractTests {

    // Переменная типа WebDriver для экземпляра драйвера:
    private static WebDriver driver;

    // Задержки для теста:
    private static Integer delayAfterTest = 1000;

    // -------------- Набор методов для организации работы основных тестов:
    // Метод для инициализации драйвера браузера Google Chrome
    @BeforeEach
    void initWebDriver() {
        WebDriverManager.chromedriver().setup();        // Набор настроек для браузера
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");            // Режим инкогнито
        //options.addArguments("--headless");
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);             // Инициализация объекта типа WebDriver опциями
        // Установка стандартной задержки (неявное ожидание) для браузера:
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void closeThisPage() throws InterruptedException {
        Thread.sleep(getDelayAfterTest());
        if(getDriver() != null) getDriver().close();    // <-- закрытие страницы
    }

    // Метод для получения экземпляра класса
    public static WebDriver getDriver() {
        return driver;
    }

    // Методы для работы с задержкой в тесте
    public static int getDelayAfterTest() {
        return delayAfterTest;
    }

}
