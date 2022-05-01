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

public abstract class AbstractTestsForMyProject {

    // Переменная типа WebDriver для экземпляра драйвера
    private static WebDriver driver;

    // Задержки для
    private static Integer delay = 500,
                           delayForJavaScript = 1000;

    // Метод для инициализации драйвера браузера Google Chrome
    @BeforeEach
    void initWebDriver() {
        // Набор настроек для браузера:
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Режим инкогнито
        options.addArguments("--incognito");
        //options.addArguments("--headless");
        options.addArguments("start-maximized");
        // Инициализация объекта типа WebDriver опциями
        driver = new ChromeDriver(options);
        // Установка стандартной задержки (неявное ожидание) для браузера
        getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @AfterEach
    void closeThisPage() throws InterruptedException {
        Thread.sleep(delay);
        getDriver().close();
        //getDriver().close();     // <-- закрытие страницы
    }

    /*
    @AfterAll
    static void closeAll() {
        // <-- закрытие браузера
        //driver.quit();
    }
    */

    // Метод для перехода по url
    boolean goToPage(String url) {
        Boolean result = false;
        String errMsg = "Запрашиваемая страница не доступна";
        Assertions.assertDoesNotThrow( ()-> driver.navigate().to(url), errMsg);
        if(!getDriver().getCurrentUrl().equals("")){
            result = true;
        }
        return result;
    }

    // Метод для получения экземпляра класса
    public static WebDriver getDriver() {
        return driver;
    }

    // Метод для проверки страницы на наличие элемента
    static Boolean itemIsAvailable(String selectElement) {
        Boolean result = null;
        // Вывод в консоль информации о работе функции:
        try {
            // Установка задержки (явного ожидания) перед каждым обращением к тестируемому элементу
            WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
            if(selectElement.contains("//")) {
                webDriverWait.until(ExpectedConditions.elementToBeSelected(By.xpath(selectElement)));
                getDriver().findElement(By.xpath(selectElement));
            } else {
                webDriverWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector(selectElement)));
                getDriver().findElement(By.cssSelector(selectElement));
            }
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            //System.out.println(e.getSupportUrl());
        }
        // Проверка условия, что элемент есть на странице:
        if (getDriver().findElement(By.xpath(selectElement)).isDisplayed() ||
            getDriver().findElement(By.cssSelector(selectElement)).isDisplayed()) {
            System.out.println("\nЭлемент: " + selectElement + " найден");
            result = true;
        } else {
            System.out.println("\nЭлемент: " + selectElement + " не найден");
            result = false;
        }
        return result;
    }

    // Метод для кликов по нужному элементу страницы
    public static Boolean actionClick(String selectElement) {
        Boolean result = null;
        // Объявление переменной типа String для формироания сообщения xpath элемента страницы:
        String headerErrMsg = "Элемент c xpath=";
        // Проверка наличия элемента
        boolean resultCheck = itemIsAvailable(selectElement);
        // Assertions на работу функции:
        Assertions.assertTrue(resultCheck, headerErrMsg + selectElement + " не найден.");
        // Если элемент имеется, то переход к созданию цепочки Actions
        if(resultCheck) {
            // Развернутая форма клика по элементу
            try {
                if(selectElement.contains("//"))
                    getDriver().findElement(By.xpath(selectElement)).click();
                else
                    getDriver().findElement(By.cssSelector(selectElement)).click();
                result = true;
            } catch (WebDriverException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getSupportUrl());
                result = false;
            }
        }
        return result;
    }

    // Метод для кликов по нужному элементу страницы и вводу данных в поле
    public static boolean actionClickAndInputData(String selectElement, String inputData) {
        boolean result = false;
        // Объявление переменной типа String для формироания сообщения xpath элемента страницы:
        String errMsg = "Элемент c xpath=";
        // Проверка наличия элемента
        Assertions.assertTrue(itemIsAvailable(selectElement));
        WebElement webElement;
        try {
            if(selectElement.contains("//")) {
                webElement = getDriver().findElement(By.xpath(selectElement));
                webElement.click();
                webElement.clear();
                webElement.sendKeys(inputData);
            } else {
                webElement = getDriver().findElement(By.cssSelector(selectElement));
                webElement.click();
                webElement.clear();
                webElement.sendKeys(inputData);
            }
            result = true;

        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSupportUrl());
            result = false;
        }

        return result;
    }

    // Метод для кликов по всплывающему окну с алертом JavaScript
    public static Integer actionAcceptJavaScript() throws InterruptedException{
        Integer result = null;
        try {
            Alert alert = getDriver().switchTo().alert();
            System.out.println("На сайте возникло дочернее окно c сообщением: " + alert.getText());
            Thread.sleep(delayForJavaScript);            // <-- задержка
            if ((alert.getText().contains("Customer added successfully")) || (alert.getText().contains("Account created successfully")))
                result = 1;
            else
                result = 0;
            getDriver().switchTo().alert().accept();     // <-- соглашаемся с alert на странице
        } catch (NoAlertPresentException e) {
            System.out.println("Сайт не выдал сообщения с подтверждением");
            e.getMessage();
        }
        return result;
    }
}
