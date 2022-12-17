package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {
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
    @DisplayName("Покупка тура в кредит с одобренной картой и валидными данными")
    void CreditAllFieldsValidApproved() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", DBHelper.getCreditPaymentStatusDB());
        assertEquals(1, DBHelper.getCreditPaymentCount());

    }

    @Test
    @DisplayName("Покупка тура в кредит с отклоненной картой и валидными данными")
    void CreditAllFieldValidDeclined() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationDeclined();
        assertEquals("DECLINED", DBHelper.getCreditPaymentStatusDB());
        assertEquals(1, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Оплата несуществующей картой")
    void CreditNonExistedCard() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardNotInBase());
        payment.waitNotificationDeclined();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Номер карты из 11 символов")
    void CreditInvalidCardNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getNumberCard11Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты прошедший месяц текущего года")
    void CreditPastMonthThisYear() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardPastMonth());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты прошедший год")
    void CreditPastYear() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardPastYear());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты 00 текущего года")
    void CreditMonth00ThisYear() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Срок действия карты 00 будущего года")
    void CreditMonth00FutureYear() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00FutureYear());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Один символ в поле Месяц")
    void DebitMonthOneNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        ;
        payment.inputData(DataHelper.getCardMonthOneNumber());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Месяц действия карты больше 12")
    void CreditMonthOver12() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Год действия карты больше 5 лет от текущего")
    void CreditYearOverCurrentPlus5() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Год действия карты 00")
    void CreditYear00() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Один символ в поле Год")
    void CreditYearOneNumber() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYearOneNumber());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца карты на кириллице")
    void CreditCyrillicCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCyrillicCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца карты из одного слова")
    void CreditOneWordCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getOneWorldCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца содержит спецсимволы")
    void CreditSpecialSymbolsCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getSpecialSymbolsCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("Имя владельца содержит цифры")
    void CreditNumberCardHolder() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getNumberCardHolder());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("CVV из двух цифр")
    void CreditCVV2Numbers() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCVV2number());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }

    @Test
    @DisplayName("CVV из одной цифры")
    void CreditCVV1Number() {
        var startPage = new StartPage();
        var payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCVV1number());
        payment.waitNotificationWrongFormat();
        assertEquals(0, DBHelper.getOrderCount());
        assertEquals(0, DBHelper.getCreditPaymentCount());
    }


}


