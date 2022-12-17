package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.dbutils.DbUtils;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.DebitCardPage;
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

    @Test
    @DisplayName("Номер карты из 11 символов")
    void DebitInvalidCardNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getNumberCard11Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
    }

    @Test
    @DisplayName("Срок действия карты прошедший месяц текущего года")
    void DebitPastMonthThisYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardPastMonth());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
    }

    @Test
    @DisplayName("Срок действия карты прошедший год")
    void DebitPastYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardPastYear());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
    }

    @Test
    @DisplayName("Срок действия карты 00 текущего года")
    void DebitMonth00ThisYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
    }

    @Test
    @DisplayName("Срок действия карты 00 будущего года")
    void DebitMonth00FutureYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonth00FutureYear());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
    }

    @Test
    @DisplayName("Один символ в поле Месяц")
    void DebitMonthOneNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonthOneNumber());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
    }

    @Test
    @DisplayName("Месяц действия карты больше 12")
    void DebitMonthOver12() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
    }

    @Test
    @DisplayName("Год действия карты больше 5 лет от текущего")
    void DebitYearOverCurrentPlus5() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
    }

}
