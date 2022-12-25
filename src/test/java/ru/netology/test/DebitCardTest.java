package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
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
        assertEquals(1, DBHelper.getOrderCount());
        assertEquals(1, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Покупка тура с отклоненной картой и валидными данными")
    void DebitAllFieldValidDeclined() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationDeclined();
        assertEquals("DECLINED", DBHelper.getPaymentStatusDB());
        assertEquals(1, DBHelper.getOrderCount());
        assertEquals(1, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Оплата несуществующей картой")
    void DebitNonExistedCard() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardNotInBase());
        payment.waitNotificationDeclined();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Номер карты из 11 символов")
    void DebitInvalidCardNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getNumberCard11Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты прошедший месяц текущего года")
    void DebitPastMonthThisYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardPastMonth());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты прошедший год")
    void DebitPastYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardPastYear());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты 00 текущего года")
    void DebitMonth00ThisYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты 00 будущего года")
    void DebitMonth00FutureYear() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonth00FutureYear());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Один символ в поле Месяц")
    void DebitMonthOneNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonthOneNumber());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Месяц действия карты больше 12")
    void DebitMonthOver12() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Год действия карты больше 5 лет от текущего")
    void DebitYearOverCurrentPlus5() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardYearOverCurrentPlus5());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Год действия карты 00")
    void DebitYear00() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Один символ в поле Год")
    void DebitYearOneNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCardYearOneNumber());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца карты на кириллице")
    void DebitCyrillicCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCyrillicCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца карты из одного слова")
    void DebitOneWordCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getOneWorldCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца содержит спецсимволы")
    void DebitSpecialSymbolsCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getSpecialSymbolsCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца содержит цифры")
    void DebitNumberCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getNumberCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("CVV из двух цифр")
    void DebitCVV2Numbers() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCVV2number());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

    @Test
    @DisplayName("CVV из одной цифры")
    void DebitCVV1Number() {
        var startPage = new StartPage();
        var payment = startPage.goToDebitPage();
        payment.inputData(DataHelper.getCVV1number());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getPaymentCount());
    }

}