package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

abstract class AbstractTestForMyProject extends AbstractTests {

    @BeforeEach
    void initMainPage(){
        String url = "https://www.saucedemo.com/",
                errMsg = "Страница не доступна";
        Assertions.assertDoesNotThrow(()-> getDriver().navigate().to(url), errMsg);
        Assertions.assertTrue(true);
    }
}
