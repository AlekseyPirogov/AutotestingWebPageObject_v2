package org.example;

import org.example.TestForCRM.AddAccountInCRM;
import org.example.TestForCRM.AddUserInCRM;
import org.example.TestForCRM.DelUserInCRM;
import org.junit.jupiter.api.*;
import com.beust.jcommander.Parameter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;

/**
 * Юнит тесты для тестирования "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login".
 */

public class AppTestForCRM extends AbstractTestsForCRM {

    public static int delay = 200;

    public static int getDelay() {
        return delay;
    }

    // #CRM1.1.Добавление нового пользователя в CRM (валидные данные)
    // Пример теста с явной проверкой результата теста (логическим умножением результатов каждого из шагов на значение предыдущего шага)
    @Order(1)
    @ParameterizedTest
    @DisplayName("#CRM1.1.Добавление нового пользователя в CRM (валидные данные)")
    @Tag("addUserInCRM")
    @Parameter
    @CsvSource({"Ivan, Ivanov, E77777"})
    void addUserInCRM(String name, String surname, String postcode) throws InterruptedException {
        AddUserInCRM addUserInCRM = new AddUserInCRM(getDriver());
        addUserInCRM
                .clickBtnBankManagerLogin()     // Переход в меню "Bank Manager Login"
                .sleep(getDelay())
                .clickBtnCustomer()             // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
                .sleep(getDelay())
                .clickBtnAddCustomer()          // На странице "Bank Manager Login" переход в меню "Add Customer" для добавления нового пользователя
                .sleep(getDelay())
                .inputFistName(name)            // Заполнение текстовых полей информацией о новом пользователе
                .inputLastName(surname)
                .inputPostCode(postcode)
                .sleep(getDelay())
                .clickBtnAddCustomerForm()     // Добавление пользователя: нажатие кнопки 'Add Customer'
                .sleep(getDelay())
                .acceptJavaScript()            // Подтверждение сообщения
                .sleep(getDelay())
                .clickBtnCustomer()            // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
                .sleep(getDelay())
                .inputInSearchField(name)     // Выбор поля для поиска: поиск по значению "name", просмотр переченя клиентов
                .sleep(getDelay());

        // Пример assertions по url
        String resUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #CRM1.2.Повторное добавление уже имеющегося пользователя пользователя в CRM (валидные данные)
    // Пример теста с проверкой результатов теста при помощи Assertions
    @Order(2)
    @ParameterizedTest
    @DisplayName("#CRM1.2.Повторное добавление уже имеющегося пользователя в CRM")
    @Tag("addUserInCRM")
    @Parameter
    @CsvSource({"Ivan, Ivanov, E77777, 2"})
    void repeatAddUserInCRM(String name, String surname, String postcode, int couterRepeat) throws InterruptedException {
        AddUserInCRM repeatAddUserInCRM = new AddUserInCRM(getDriver());
        repeatAddUserInCRM
                .clickBtnBankManagerLogin()     // Переход в меню "Bank Manager Login"
                .sleep(getDelay())
                .clickBtnCustomer()            // На странице "Bank Manager Login" переход в меню "Customers"
                .sleep(getDelay());                 // для просмотра информации о пользователях

        for(int i=0; i < couterRepeat; i++)
            repeatAddUserInCRM
                    .clickBtnAddCustomer()          // На странице "Bank Manager Login" переход в меню "Add Customer" для добавления нового пользователя
                    .sleep(getDelay())
                    .inputFistName(name)            // Заполнение текстовых полей информацией о новом пользователе
                    .inputLastName(surname)
                    .inputPostCode(postcode)
                    .sleep(getDelay())
                    .clickBtnAddCustomerForm()     // Добавление пользователя: нажатие кнопки 'Add Customer'
                    .sleep(getDelay())
                    .acceptJavaScript()            // Подтверждение сообщения
                    .sleep(getDelay())
                    .clickBtnCustomer()            // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
                    .sleep(getDelay())
                    .inputInSearchField(name)     // Выбор поля для поиска: поиск по значению "name", просмотр переченя клиентов
                    .sleep(getDelay());

        // Пример assertions по url
        String resUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #CRM1.3.Добавление нового пользователя в CRM (невалидные данные по каждому полей)
    @Order(3)
    @ParameterizedTest
    @DisplayName("#CRM1.3. Добавление нового пользователя в CRM (невалидные данные по каждому из полей)")
    @Tag("addUserInCRMWithInvalidData")
    @Parameter
    @CsvSource({"-0.1, Ivanov, E77777",
                "Ivan, 0.0, E77777",
                "Ivan, Ivanov, 0.1",
                "-0.0, 0.0, -0.0"})
    void addUserInCRMWithInvalidData(String name, String surname, String postcode) throws InterruptedException {
        AddUserInCRM addUserInCRM = new AddUserInCRM(getDriver());
        addUserInCRM
                .clickBtnBankManagerLogin()     // Переход в меню "Bank Manager Login"
                .clickBtnCustomer()             // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
                .clickBtnAddCustomer()          // На странице "Bank Manager Login" переход в меню "Add Customer" для добавления нового пользователя
                .inputFistName(name)            // Заполнение текстовых полей информацией о новом пользователе
                .inputLastName(surname)
                .inputPostCode(postcode)
                .clickBtnAddCustomerForm()     // Добавление пользователя: нажатие кнопки 'Add Customer'
                .acceptJavaScript()            // Подтверждение сообщения
                .clickBtnCustomer()            // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
                .inputInSearchField(name);     // Выбор поля для поиска: поиск по значению "name", просмотр переченя клиентов

        // Пример assertions для элемента
        Boolean resultTest = true;
        if (getDriver().findElement(By.xpath("//tr[contains(.,'" + name + "')]")).getText().contains(name) ||
            getDriver().findElement(By.xpath("//tr[contains(.,'" + name + "')]")).getText().contains(surname) ||
            getDriver().findElement(By.xpath("//tr[contains(.,'" + name + "')]")).getText().contains(postcode))
            resultTest = false;

        Assertions.assertFalse(resultTest);
    }

    // #CRM2.Добавление банковского счета для пользователя
    @Order(4)
    @ParameterizedTest
    @DisplayName("#CRM2.Добавление банковского счета для пользователя")
    @Tag("addAccountInCRM")
    @Parameter
    @CsvSource({"Ivan, Ivanov, E77777"})
    void addAccountInCRM(String name, String surname, String postcode) throws InterruptedException {
        AddAccountInCRM addAccountInCRM = new AddAccountInCRM(getDriver());
        addAccountInCRM
                .clickBtnBankManagerLogin()     // Переход в меню "Bank Manager Login"
                .clickBtnCustomer()             // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
                .sleep(getDelay())
                .clickBtnAddCustomer()          // На странице "Bank Manager Login" переход в меню "Add Customer" для добавления нового пользователя
                .sleep(getDelay())
                .inputFistName(name)            // Заполнение текстовых полей информацией о новом пользователе
                .inputLastName(surname)
                .inputPostCode(postcode)
                .sleep(getDelay())
                .clickBtnAddCustomerForm()      // Добавление пользователя: нажатие кнопки 'Add Customer'
                .sleep(getDelay())
                .acceptJavaScript()             // Подтверждение сообщения
                .openAccount()                  // Переход в меню "Customers" для открытия счёта ("Open Account")
                .selectUser()                   // Выбор поля для поиска: просмотр переченя клинетов, выбор клиента
                .selectCurrency()               // Просмотр типов валютных счётов
                .sleep(getDelay())
                .clickBtnProcess()              // Открытие счёта
                .acceptJavaScript()             // Обработка исключения после появления окна c сообщением на странице
                .sleep(getDelay())
                .clickBtnCustomer()             // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
                .sleep(getDelay())
                .inputInSearchField(name)      // Выбор поля для поиска: поиск по значению "name", просмотр переченя клиентов
                .sleep(getDelay() * 3);

        // Пример assertions по url
        String resUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }

    // #CRM3. Удаление пользователя
    @Order(5)
    @Test
    @Tag("delUserInCRM")
    @DisplayName("#CRM3.Удаление пользователя")
    void delUserInCRM() throws InterruptedException {
        DelUserInCRM delUserInCRM = new DelUserInCRM(getDriver());
        delUserInCRM
                .clickBtnBankManagerLogin()     // Переход в меню "Bank Manager Login"
                .clickBtnCustomer()
                .sleep(getDelay() * 3)
                .deleteUser("Neville")
                .sleep(getDelay() * 3)
                .deleteUser("Ron")
                .sleep(getDelay() * 3)
                .deleteUser("Harry")         // На странице "Bank Manager Login" переход в меню "Customers"
                .sleep(getDelay() * 3);

        // Пример assertions по url
        String resUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list";
        Assertions.assertEquals(resUrl, getDriver().getCurrentUrl());
    }
}
