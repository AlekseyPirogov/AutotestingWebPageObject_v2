package org.example;

import org.example.TestForMyProject.WorkWithApp;
import org.junit.jupiter.api.*;
import com.beust.jcommander.Parameter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Тесты для www.saucedemo.com
 */

public class AppTestForMyProject extends AbstractTestForMyProject {

    public static int delay = 200;

    public static int getDelay() {
        return delay;
    }

    // #MyProject_1.Добавление пользователя (заблокированный и отсутствующий пользователь)
    @Test
    @DisplayName("#MyProject_1 Добавление пользователя (заблокированный и отсутствующий пользователь)")
    @Tag("userRegistration")
    @Tag("#MyProject_1")
    void userRegistration() throws InterruptedException {
        WorkWithApp workWithApp = new WorkWithApp(getDriver());
        workWithApp                                // Ввод данных
                .inputUsername("locked_out_user")
                .inputPassword("secret_sauce")
                .sleep(getDelay())
                .clickLoginButton()                 // Аутентификация пользователя
                .sleep(getDelay() * 2)
                .clickReloadAuth();                  // Нажатие кнопки с ошибкой для сброса аутентификации

        // Пример assertions по url
        String resUrl = "https://www.saucedemo.com/";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());

        workWithApp
                .inputUsername("nobody")
                .inputPassword("zero")
                .sleep(getDelay())
                .clickLoginButton()                // Аутентификация пользователя
                .sleep(getDelay() * 5);

        // Результат работы теста assertions по url
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #MyProject_2.Регистрация пользователя и выход из витрины (низкая производительность)
    @ParameterizedTest
    @DisplayName("#MyProject_2 Регистрация пользователя и выход из витрины (низкая производительность)")
    @Tag("userRegistrationAndExit")
    @Tag("#MyProject_2")
    @Parameter
    @CsvSource({"performance_glitch_user, secret_sauce"})
    void userRegistrationAndExit(String user, String password) throws InterruptedException {
        WorkWithApp workWithApp = new WorkWithApp(getDriver());
        workWithApp                                // Ввод данных
                .inputUsername(user)
                .inputPassword(password)
                .sleep(getDelay())
                .clickLoginButton()                 // Аутентификация пользователя
                .sleep(getDelay())
                .openMainMenu()                     // Открытие верхнего левого меню
                .sleep(getDelay())
                .loguot()                           // Выход из витрины
                .sleep(getDelay() * 5);

        // Результат работы теста assertions по url
        String resUrl = "https://www.saucedemo.com/";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #MyProject_3.Работа с пользовательским интерфейсом
    @ParameterizedTest
    @DisplayName("#MyProject_3 Работа с пользовательским интерфейсом")
    @Tag("workWithUI")
    @Tag("#MyProject_3")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void workWithUI(String user, String password) throws InterruptedException {
        WorkWithApp workWithApp = new WorkWithApp(getDriver());
        workWithApp                         // Ввод данных
                .inputUsername(user)
                .inputPassword(password)
                .clickLoginButton()         // Аутентификация пользователя
                .sleep(getDelay())
                .goToFotter()               // Скролл странцы: переход в футер
                .sleep(getDelay())
                .openMainMenu()             // Переход в главное меню
                .sleep(getDelay())
                .closeMainMenu()            // Закрытие главного меню
                .sleep(getDelay())
                .selectFieldSort()          // Проверка доступности меню сортировки товаров на странице витрины
                .sleep(getDelay())
                .sortByAZ()                 // Сортировка в прямом алфавитном порядке
                .sleep(getDelay())
                .sortByZA()                 // Сортировка в обратном алфавитном порядке
                .sleep(getDelay())
                .openShoppingCart()         // Переход в корзину
                .sleep(getDelay())
                .closeShoppingCart()        // Выход из корзины
                .sleep(getDelay() * 7)
                .viewItem(4)                // Просмотр товара
                .sleep(getDelay() * 7)
                .backToProducts()           // Возврат к покупкам
                .sleep(getDelay() * 3)
                .viewItem(3)                // Просмотр товара
                .sleep(getDelay() * 6)
                .openShoppingCart()         // Переход в корзину
                .openMainMenu()             // Открытие верхнего левого меню
                .loguot()                  // Выход из витрины
                .sleep(getDelay() * 3);

        // Результат работы теста assertions по url
        String resUrl = "https://www.saucedemo.com/";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #MyProject_4.Сортировка товаров витрины
    @ParameterizedTest
    @DisplayName("#MyProject_4 Сортировка товаров витрины")
    @Tag("sortItem")
    @Tag("#MyProject_4")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void sortItem(String user, String password) throws InterruptedException {
        WorkWithApp workWithApp = new WorkWithApp(getDriver());
        workWithApp                     // Ввод данных
                .inputUsername(user)
                .inputPassword(password)
                .clickLoginButton()     // Аутентификация пользователя
                .sleep(getDelay())
                .selectFieldSort()      // Проверка доступности меню сортировки товаров на странице витрины
                .sleep(getDelay() * 4)
                .sortByAZ()             // Сортировка в прямом алфавитном порядке
                .sleep(getDelay() * 4)
                .sortByZA()             // Сортировка в обратном алфавитном порядке
                .sleep(getDelay() * 4)
                .sortByHilo()           // Сортировка в порядке убывания цены
                .sleep(getDelay() * 4)
                .sortByLohi()          // Сортировка в порядке возрастания цены
                .sleep(getDelay() * 4);

        // Результат работы теста assertions по url
        String resUrl = "https://www.saucedemo.com/inventory.html";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #MyProject_5.Добавление товаров в корзину
    @ParameterizedTest
    @DisplayName("#MyProject_5 Добавление товаров в корзину")
    @Tag("aadItemInBasket")
    @Tag("#MyProject_5")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void aadItemInBasket(String user, String password) throws InterruptedException {
        WorkWithApp workWithApp = new WorkWithApp(getDriver());
        workWithApp                     // Ввод данных
                .inputUsername(user)
                .inputPassword(password)
                .clickLoginButton()     // Аутентификация пользователя
                .sleep(getDelay() * 3)
                .addToCartItem("sauce-labs-backpack")   // Добавление товаров витрины в корзину
                .addToCartItem("sauce-labs-onesie")
                .addToCartItem("sauce-labs-fleece-jacket")
                .sleep(getDelay() * 2)
                .openShoppingCart()     // Переход в корзину
                .sleep(getDelay() * 4)
                .removeItemInCart("sauce-labs-onesie")
                .removeItemInCart("sauce-labs-backpack")
                .continueShopping()    // Возврат к покупкам
                .sleep(getDelay() * 3)
                .addToCartItem("sauce-labs-bike-light")
                .openShoppingCart()     // Переход в корзину
                .sleep(getDelay() * 4)
                .openMainMenu()         // Переход в главное меню
                .sleep(getDelay() * 2)
                .loguot()               // Выход из витрины
                .sleep(getDelay() * 5)
                .inputUsername(user)    // Ввод данных
                .inputPassword(password)
                .clickLoginButton()     // Аутентификация пользователя
                .openShoppingCart()
                .sleep(getDelay() * 3)
                .removeItemInCart("sauce-labs-fleece-jacket")
                .removeItemInCart("sauce-labs-bike-light")
                .sleep(getDelay() * 10);

        // Результат работы теста assertions по url
        String resUrl = "https://www.saucedemo.com/cart.html";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #MyProject_6.Оформление заказа
    @ParameterizedTest
    @DisplayName("#MyProject_6 Оформление заказа")
    @Tag("createOrder")
    @Tag("#MyProject_6")
    @Parameter
    @CsvSource({"standard_user, secret_sauce, Ivan, Ivanov, E77777"})
    void createOrder(String user, String password, String name, String surname, String postcode) throws InterruptedException {
        WorkWithApp workWithApp = new WorkWithApp(getDriver());
        workWithApp                     // Ввод данных
                .inputUsername(user)
                .inputPassword(password)
                .clickLoginButton()     // Аутентификация пользователя
                .sleep(getDelay())
                .addToCartItem("sauce-labs-onesie")         // Добавление товаров витрины в корзину
                .addToCartItem("sauce-labs-fleece-jacket")
                .sleep(getDelay() * 2)
                .openShoppingCart()                         // Переход в корзину
                .sleep(getDelay() * 2)
                .checkout()             // Переход к оформлению заказа
                .sleep(getDelay() * 3)
                .inputFirstName(name)   // Заполнить тектовые поля с информацией о новом пользователе:
                .inputLastName(surname)
                .inputPostCode(postcode)
                .sleep(getDelay())
                .continueProc()
                .sleep(getDelay() * 5)
                .goToFotter()           // Переход в футер: cкролл странцы
                .finishProc()
                .goToHeader()
                .sleep(getDelay() * 5)
                .backToProducts()       // Назад к покупкам
                .sleep(getDelay() * 5);

        // Результат работы теста assertions по url
        String resUrl = "https://www.saucedemo.com/inventory.html";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #MyProject_7.Проверка работы кнопки главного меню "Reset App State"
    @ParameterizedTest
    @DisplayName("#MyProject_7 Проверка работы кнопки главного меню \"Reset App State\"")
    @Tag("resetAppState")
    @Tag("#MyProject_7")
    @Parameter
    @CsvSource({"standard_user, secret_sauce"})
    void resetAppState(String user, String password) throws InterruptedException {
        WorkWithApp workWithApp = new WorkWithApp(getDriver());
        workWithApp                         // Ввод данных
                .inputUsername(user)
                .inputPassword(password)
                .sleep(getDelay())
                .clickLoginButton()         // Аутентификация пользователя
                .openShoppingCart()         // Переход в корзину
                .sleep(getDelay() * 3)
                .closeShoppingCart()        // Возврат к покупкам
                .sleep(getDelay() * 3)
                .addToCartItem("sauce-labs-backpack")       // Добавление товаров витрины в корзину
                .sleep(getDelay() * 3)
                .addToCartItem("sauce-labs-onesie")
                .sleep(getDelay() * 3)
                .addToCartItem("sauce-labs-fleece-jacket")
                .sleep(getDelay() * 5)
                .openMainMenu()             // Переход в главное меню
                .sleep(getDelay() * 5)
                .resetUI()                  // Сброс приложения
                .openShoppingCart()         // Переход в корзину
                .sleep(getDelay() * 6);

        // Результат работы теста assertions по url
        String resUrl = "https://www.saucedemo.com/cart.html";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }
}
