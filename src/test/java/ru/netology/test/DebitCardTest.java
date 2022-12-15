package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.dbutils.DbUtils;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080");
        //Configuration.headless = true;
    }

    @AfterEach
    public void cleanBase() {
        DBHelper.cleanDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Покупка тура с одобренной картой и валидными данными")
    void DebitAllFieldsValidApproved() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", DBHelper.getPaymentStatusDB());
    }


    @Test
    @DisplayName("Покупка тура с отклоненной картой и валидными данными")
    void DebitAllFieldValidDeclined() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationDeclined();
        assertEquals("DECLINED", DBHelper.getPaymentStatusDB());
    }



    @Test
    @DisplayName("Оплата несуществующей картой")
    void DebitNonExistedCard() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardNotInBase());
        payment.waitNotificationDeclined();
        assertEquals(0, DBHelper.getOrderCount());
    }



}
