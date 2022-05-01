package org.example;

import org.junit.jupiter.api.*;
import com.beust.jcommander.Parameter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit test for simple App.
 */
public class AppTestForCRM extends AbstractTestsForMyProject {

    // Ссылка для работы тестов:
    static String Url = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";

    // Задержка для тестов:
    static int delay = 200;

    // #CRM0.Проверка страницы для открытия
    // Пример передачи URL в качестве параметра
    @Order(0)
    @ParameterizedTest
    @DisplayName("#CRM0.Проверка страницы на доступность для открытия")
    @Parameter
    @ValueSource(strings = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login")
    void openPage(String url) {
        Assertions.assertTrue(goToPage(url));
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

        // Переменная для хранения результата шага и результата теста
        Boolean resultStep = false;

        // Переход по ссылке
        resultStep |= goToPage(Url);
        Assertions.assertTrue(resultStep);

        // Переход в меню "Bank Manager Login"
        resultStep &= actionClick("//button[contains(.,'Bank Manager Login')]");
        Assertions.assertTrue(resultStep);
        // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
        resultStep &= actionClick("//button[contains(.,'Customers')]");
        Assertions.assertTrue(resultStep);
        // На странице "Bank Manager Login" переход в меню "Add Customer" для добавления нового пользователя
        resultStep &= actionClick("//button[contains(.,'Add Customer')]");
        Assertions.assertTrue(resultStep);

        // Заполнение текстовых полей информацией о новом пользователе
        resultStep &= actionClickAndInputData("//input[@placeholder='First Name']", name);
        Assertions.assertTrue(resultStep);
        resultStep &= actionClickAndInputData("//input[@placeholder='Last Name']", surname);
        Assertions.assertTrue(resultStep);
        resultStep &= actionClickAndInputData("//input[@placeholder='Post Code']", postcode);
        Assertions.assertTrue(resultStep);
        Thread.sleep(delay);
        // Добавление пользователя: нажатие кнопки 'Add Customer'
        resultStep &= actionClick("//form/button[contains(.,'Add Customer')]");
        Assertions.assertTrue(resultStep);

        // Обработка исключения из-за появления окна c сообщением на странице
        resultStep = (actionAcceptJavaScript() == 1) ? true : false;
        Assertions.assertTrue(resultStep);

        // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
        resultStep &= actionClick("//button[contains(.,'Customers')]");
        Assertions.assertTrue(resultStep);
        // Выбор поля для поиска: поиск по значению "Ivan", просмотр переченя клиентов
        resultStep &= actionClickAndInputData("//input[@placeholder='Search Customer']", name);
        Assertions.assertTrue(resultStep);

        // Итоговый Assertions для теста
        Assertions.assertTrue(resultStep);
    }

    // #CRM1.2.Повторное добавление уже имеющегося пользователя пользователя в CRM (валидные данные)
    // Пример теста с проверкой результатов теста при помощи Assertions
    @Order(2)
    @ParameterizedTest
    @DisplayName("#CRM1.2.Повторное добавление уже имеющегося пользователя в CRM")
    @Tag("addUserInCRM")
    @Parameter
    @CsvSource({"Ivan, Ivanov, E77777"})
    void repeatAddUserInCRM(String name, String surname, String postcode) throws InterruptedException {
        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Переход в меню "Bank Manager Login"
        Assertions.assertTrue(actionClick("//button[contains(.,'Bank Manager Login')]"));
        // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
        Assertions.assertTrue(actionClick("//button[contains(.,'Customers')]"));

        for(int i=0; i<2; i++) {
            // На странице "Bank Manager Login" переход в меню "Add Customer" для добавления нового пользователя
            Assertions.assertTrue(actionClick("//button[contains(.,'Add Customer')]"));

            // Заполнение текстовых полей информацией о новом пользователе
            Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='First Name']", name));
            Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Last Name']", surname));
            Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Post Code']", postcode));
            Thread.sleep(delay);
            // Добавление пользователя: нажатие кнопки 'Add Customer'
            Assertions.assertTrue(actionClick("//form/button[contains(.,'Add Customer')]"));

            // Обработка исключения из-за появления окна c сообщением на странице
            Integer resFirst = actionAcceptJavaScript();
            Assertions.assertNotNull(resFirst);

            // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
            Assertions.assertTrue(actionClick("//button[contains(.,'Customers')]"));
            // Выбор поля для поиска: поиск по значению "Ivan", просмотр переченя клиентов
            Assertions.assertTrue(actionClick("//input[@placeholder='Search Customer']"));
            Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Search Customer']", name));
        }
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
        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Переход в меню "Bank Manager Login"
        Assertions.assertTrue(actionClick("//button[contains(.,'Bank Manager Login')]"));
        // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
        Assertions.assertTrue(actionClick("//button[contains(.,'Customers')]"));
        // На странице "Bank Manager Login" переход в меню "Add Customer" для добавления нового пользователя
        Assertions.assertTrue(actionClick("//button[contains(.,'Add Customer')]"));
        // Заполнение текстовых полей информацией о новом пользователе
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='First Name']", name));
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Last Name']", surname));
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Post Code']", postcode));
        Thread.sleep(delay);
        // Добавление пользователя: нажатие кнопки 'Add Customer'
        Assertions.assertTrue(actionClick("//form/button[contains(.,'Add Customer')]"));

        // Обработка исключения из-за появления окна c сообщением на странице
        Integer resFirst = actionAcceptJavaScript();
        Assertions.assertNotNull(resFirst);

        // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
        Assertions.assertTrue(actionClick("//button[contains(.,'Customers')]"));
        // Выбор поля для поиска: поиск по значению "Ivan", просмотр переченя клиентов
        Assertions.assertTrue(actionClick("//input[@placeholder='Search Customer']"));
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Search Customer']", name));
    }

    // #CRM2.Добавление банковского счета для пользователя
    @Order(4)
    @ParameterizedTest
    @DisplayName("#CRM2.Добавление банковского счета для пользователя")
    @Tag("addAccountInCRM")
    @Parameter
    @CsvSource({"Ivan, Ivanov, E77777"})
    void addAccountInCRM(String name, String surname, String postcode) throws InterruptedException {
        // Переход по ссылке
        Assertions.assertTrue(goToPage(Url));

        // Переход в меню "Bank Manager Login"
        Assertions.assertTrue(actionClick("//button[contains(.,'Bank Manager Login')]"));
        // Переход в меню "Customers" для просмотра информации о пользователях
        Assertions.assertTrue(actionClick("//button[contains(.,'Customers')]"));
        // Переход в меню "Add Customer" для добавления нового пользователя
        Assertions.assertTrue(actionClick("//button[contains(.,'Add Customer')]"));

        // Заполнение текстовых полей с информацией о новом пользователе
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='First Name']", name));
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Last Name']", surname));
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Post Code']", postcode));
        Thread.sleep(delay);
        Assertions.assertTrue(actionClick("//form/button[contains(.,'Add Customer')]"));

        // Обработка исключения после появления alert c сообщением на странице
        actionAcceptJavaScript();

        // Переход в меню "Customers" для открытия счёта ("Open Account")
        Assertions.assertTrue(actionClick("//button[contains(.,'Open Account')]"));

        // Выбор поля для поиска: просмотр переченя клинетов, выбор клиента
        Assertions.assertTrue(actionClick("//select[@id=\"userSelect\"]/option[contains(.,'" + name + " " + surname + "')]"));
        // Просмотр типов валютных счётов
        Assertions.assertTrue(actionClick("//*[@id=\"currency\"]/option[contains(.,'Dollar')]"));
        // Открытие счёта
        Assertions.assertTrue(actionClick("//form/button[contains(.,'Process')]"));

        // Обработка исключения после появления окна c сообщением на странице
        Assertions.assertEquals(1, actionAcceptJavaScript());

        // На странице "Bank Manager Login" перейти в меню "Customers" для просмотра информации о пользователях
        Assertions.assertTrue(actionClick("//button[contains(.,'Customers')]"));
        // Выбор поля для поиска и ввод в него значения "Ivan", просмотр перечень клинетов
        Assertions.assertTrue(actionClickAndInputData("//input[@placeholder='Search Customer']", name));
        Thread.sleep(delay * 3);
    }

    // #CRM3. Удаление пользователя
    @Order(5)
    @Test
    @Tag("delUserInCRM")
    @DisplayName("#CRM3.Удаление пользователя")
    void delUserInCRM() throws InterruptedException {
        String params[] = {"Harry", "Neville", "Ron"};
        // Шаг 1. Подготовка к выполению теста: переход по ссылке
        Assertions.assertTrue(goToPage(Url));
        // Переход в меню "Bank Manager Login"
        Assertions.assertTrue(actionClick("//button[contains(.,'Bank Manager Login')]"));
        // На странице "Bank Manager Login" переход в меню "Customers" для просмотра информации о пользователях
        Assertions.assertTrue(actionClick("//button[contains(.,'Customers')]"));
        // Выбор пользователя и его удаление
        for (String name: params) {
            Assertions.assertTrue(actionClick("//tr[contains(.,'" + name + "')]/td[*]/button[contains(.,'Delete')]"));
            //Альтернативный xpath
            // Assertions.assertTrue(actionClick("//*[contains(.,'" + name + "')]/*/button[contains(.,'Delete')]"));
            Thread.sleep(delay * 3);
        }
    }
}
