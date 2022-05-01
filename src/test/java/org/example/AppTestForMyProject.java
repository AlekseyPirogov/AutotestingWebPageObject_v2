package org.example;

import org.junit.jupiter.api.*;
import com.beust.jcommander.Parameter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit test for simple App.
 */

public class AppTestForMyProject extends AbstractTestsForMyProject {

    // Ссылка для работы тестов:
    static String Url = "https://www.saucedemo.com/";

    // Задержка для тестов:
    static int delay = 200;

    // #MyProject_0.Открытие страницы
    @ParameterizedTest
    @DisplayName("#MyProject_0 Проверка страницы на доступность для открытия")
    @Tag("openPage")
    @Tag("#MyProject_0")
    @Parameter
    @ValueSource(strings = "https://www.saucedemo.com/")
    // Пример передачи URL в качестве параметра
    void openPage(String url) throws InterruptedException {
        Assertions.assertTrue(goToPage(url));
    }

    // #MyProject_1.Добавление пользователя (заблокированный и отсутствующий пользователь)
    @ParameterizedTest
    @DisplayName("#MyProject_1 Добавление пользователя (заблокированный и отсутствующий пользователь)")
    @Tag("userRegistration")
    @Tag("#MyProject_1")
    @Parameter
    @CsvSource({"locked_out_user, secret_sauce"})
    void userRegistration(String user, String password) throws InterruptedException {

        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Заполнение текстовых полей для аутентификации пользователя с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", "locked_out_user");
        actionClickAndInputData("//input[@placeholder='Password']", "secret_sauce");

        // Аутентификация пользователя
        actionClick("//input[@value='Login']");
        // Нажатие кнопки с ошибкой для сброса аутентификации
        actionClick("//h3[@data-test='error']/button");

        // Заполнение текстовых полей для аутентификации пользователя с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", "nobody");
        actionClickAndInputData("//input[@placeholder='Password']", "zero");

        // Аутентификация пользователя
        actionClick("//input[@value='Login']");
        Thread.sleep(delay*3);
    }

    // #MyProject_2.Регистрация пользователя и выход из витрины (низкая производительность)
    @ParameterizedTest
    @DisplayName("#MyProject_2 Регистрация пользователя и выход из витрины (низкая производительность)")
    @Tag("userRegistrationAndExit")
    @Tag("#MyProject_2")
    @Parameter
    @CsvSource({"performance_glitch_user, secret_sauce"})
    void userRegistrationAndExit(String user, String password) throws InterruptedException{

        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Заполнение текстовых полей для аутентификации пользователя с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", user);
        actionClickAndInputData("//input[@placeholder='Password']", password);

        // Аутентификация пользователя
        Thread.sleep(delay);
        actionClick("//input[@value='Login']");

        // Открытие верхнего левого меню
        actionClick("//button[@id='react-burger-menu-btn']");
        Thread.sleep(delay);
        // Выход из аккаунта
        actionClick("//a[contains(text(),'Logout')]");
        Thread.sleep(delay * 3);
    }

    // #MyProject_3.Работа с пользовательским интерфейсом
    @ParameterizedTest
    @DisplayName("#MyProject_3 Работа с пользовательским интерфейсом")
    @Tag("workWithUI")
    @Tag("#MyProject_3")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void workWithUI(String user, String password) throws InterruptedException {

        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Заполнение текстовых полей для аутентификации пользователя
        // с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", user);
        actionClickAndInputData("//input[@placeholder='Password']", password);
        // Аутентификация пользователя
        actionClick("//input[@value='Login']");
        Thread.sleep(delay);

        // Скролл странцы: переход в футер
        actionClick("//div[@id='page_wrapper']/footer/div");
        Thread.sleep(delay);

        // Переход в главное меню
        actionClick("//button[@id='react-burger-menu-btn']");
        Thread.sleep(delay);
        // Закрытие главного меню
        actionClick("//button[@id='react-burger-cross-btn']");
        Thread.sleep(delay);

        // Проверка доступности меню сортировки товаров на странице витрины
        // Сортировка в прямом алфавитном порядке
        actionClick("//div[@id='header_container']/div[2]/div[2]/span/select");
        actionClick("//div[@id='header_container']/div[2]/div[2]/span/select/option[@value='az']");
        Thread.sleep(delay);

        // Сортировка в обратном алфавитном порядке
        //checkItem("//div[@id='header_container']/div[2]/div[2]/span/select");
        actionClick("//div[@id='header_container']/div[2]/div[2]/span/select/option[@value='za']");
        Thread.sleep(delay);

        // Переход в корзину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay * 2);

        // Выход из корзины
        actionClick("//button[@id='continue-shopping']");
        Thread.sleep(delay);

        // Просмотр товара
        actionClick("//a[@id='item_4_img_link']/img");
        Thread.sleep(delay * 2);

        // Возврат к покупкам
        actionClick("//button[@id='back-to-products']");
        Thread.sleep(delay);

        // Просмотр товара
        actionClick("//a[@id='item_3_img_link']/img");
        Thread.sleep(delay * 2);

        // Переход в коризину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay * 2);

        // Переход в главное меню
        actionClick("//button[@id='react-burger-menu-btn']");
        Thread.sleep(delay);

        // Выход из витрины
        actionClick("//*[@id='logout_sidebar_link']");
        Thread.sleep(delay * 3);
    }

    // #MyProject_4.Сортировка товаров витрины
    @ParameterizedTest
    @DisplayName("#MyProject_4 Сортировка товаров витрины")
    @Tag("sortItem")
    @Tag("#MyProject_4")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void sortItem(String user, String password) throws InterruptedException {

        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Заполнение текстовых полей для аутентификации пользователя
        // с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", user);
        actionClickAndInputData("//input[@placeholder='Password']", password);
        // Аутентификация пользователя
        actionClick("//input[@value='Login']");

        // Проверка доступности меню сортировки товаров на странице витрины
        // Сортировка в прямом алфавитном порядке
        actionClick("//*[@value='az']");
        Thread.sleep(delay * 2);

        // Сортировка в обратном алфавитном порядке
        actionClick("//*[@value='za']");
        Thread.sleep(delay * 2);

        // Сортировка в порядке убывания цены
        actionClick("//*[@value='hilo']");
        Thread.sleep(delay * 2);

        // Сортировка в порядке возрастания цены
        actionClick("//*[@value='lohi']");
        Thread.sleep(delay * 3);
    }

    // #MyProject_5.Добавление товаров в корзину
    @ParameterizedTest
    @DisplayName("#MyProject_5 Добавление товаров в корзину")
    @Tag("aadItemInBasket")
    @Tag("#MyProject_5")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void aadItemInBasket(String user, String password) throws InterruptedException {

        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Заполнение текстовых полей для аутентификации пользователя
        // с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", user);
        actionClickAndInputData("//input[@placeholder='Password']", password);
        // Аутентификация пользователя
        actionClick("//input[@value='Login']");

        // Добавление товаров витрины в корзину
        actionClick("//*[@id='add-to-cart-sauce-labs-backpack']");
        Thread.sleep(delay);

        actionClick("//*[@id='add-to-cart-sauce-labs-onesie']");
        Thread.sleep(delay);

        actionClick("//*[@id='add-to-cart-sauce-labs-fleece-jacket']");
        Thread.sleep(delay);

        // Переход в корзину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay);

        // Удаление товаров
        actionClick("//button[@id='remove-sauce-labs-onesie' and contains(.,'Remove')]");
        Thread.sleep(delay * 2);

        actionClick("//button[contains(.,'Remove') and @id='remove-sauce-labs-backpack']");
        Thread.sleep(delay * 2);

        // Возврат к покупкам
        actionClick("//*[@id='continue-shopping']");
        Thread.sleep(delay);

        actionClick("//button[@id='add-to-cart-sauce-labs-bike-light']");
        Thread.sleep(delay);

        // Переход в корзину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay * 2);

        // Переход в главное меню
        actionClick("//button[@id='react-burger-menu-btn']");
        Thread.sleep(delay);

        // Выход из витрины
        actionClick("//*[@id='logout_sidebar_link']");
        Thread.sleep(delay);

        // Заполнение текстовых полей для аутентификации пользователя
        // с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", user);
        actionClickAndInputData("//input[@placeholder='Password']", password);
        // Аутентификация пользователя
        actionClick("//input[@value='Login']");

        // Переход в корзину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay * 2);

        // Удаление товаров из корзины
        actionClick("//button[contains(.,'Remove') and @id='remove-sauce-labs-fleece-jacket']");
        Thread.sleep(delay * 2);
        actionClick("//button[contains(.,'Remove') and @id='remove-sauce-labs-bike-light']");
        Thread.sleep(delay * 3);
    }

    // #MyProject_6.Оформление заказа
    @ParameterizedTest
    @DisplayName("#MyProject_6 Оформление заказа")
    @Tag("createOrder")
    @Tag("#MyProject_6")
    @Parameter
    @CsvSource({"standard_user, secret_sauce, Ivan, Ivanov, E77777"})
    void createOrder(String user, String password, String name, String surname, String postcode) throws InterruptedException {

        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Заполнение текстовых полей для аутентификации пользователя
        // с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", user);
        actionClickAndInputData("//input[@placeholder='Password']", password);
        // Аутентификация пользователя
        actionClick("//input[@value='Login']");

        // Добавление товаров витрины в корзину
        actionClick("//*[@id='add-to-cart-sauce-labs-onesie']");
        Thread.sleep(delay);

        actionClick("//*[@id='add-to-cart-sauce-labs-fleece-jacket']");
        Thread.sleep(delay);

        // Переход в корзину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay * 2);

        // Переход к оформлению заказа
        actionClick("//button[@id='checkout']");
        Thread.sleep(delay * 2);

        // Заполнить тектовые поля с информацией о новом пользователе:
        actionClickAndInputData("//input[@id='first-name']", name);
        actionClickAndInputData("//input[@id='last-name']", surname);
        actionClickAndInputData("//input[@id='postal-code']", postcode);
        Thread.sleep(delay * 2);
        actionClick("//input[@id='continue']");
        Thread.sleep(delay * 2);

        // Переход в футер: cкролл странцы
        actionClick("//div[@id='page_wrapper']/footer/div");
        actionClick("//button[@id='finish']");
        actionClick("//*[@id='header_container']");
        Thread.sleep(delay * 2);

        actionClick("//button[@id='back-to-products']");
        Thread.sleep(delay * 3);
    }

    // #MyProject_7.Проверка работы кнопки главного меню "Reset App State"
    @ParameterizedTest
    @DisplayName("#MyProject_7 Проверка работы кнопки главного меню \"Reset App State\"")
    @Tag("resetAppState")
    @Tag("#MyProject_7")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void resetAppState(String user, String password) throws InterruptedException {

        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Заполнение текстовых полей для аутентификации пользователя
        // с использованием xPath:
        actionClickAndInputData("//input[@placeholder='Username']", user);
        actionClickAndInputData("//input[@placeholder='Password']", password);
        // Аутентификация пользователя
        actionClick("//input[@value='Login']");

        // Добавление товаров витрины в корзину
        actionClick("//*[@id='add-to-cart-sauce-labs-backpack']");
        Thread.sleep(delay);

        actionClick("//*[@id='add-to-cart-sauce-labs-onesie']");
        Thread.sleep(delay);

        actionClick("//*[@id='add-to-cart-sauce-labs-fleece-jacket']");
        Thread.sleep(delay);

        // Переход в корзину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay);

        // Возврат к покупкам
        actionClick("//*[@id='continue-shopping']");
        Thread.sleep(delay);

        // Переход в главное меню
        actionClick("//button[@id='react-burger-menu-btn']");
        Thread.sleep(delay);

        // Сброс приложения
        actionClick("//*[@id=\"reset_sidebar_link\"]");
        Thread.sleep(delay * 3);

        // Переход в корзину
        actionClick("//div[@id='shopping_cart_container']/a");
        Thread.sleep(delay);
        Thread.sleep(delay * 3);
    }
}
