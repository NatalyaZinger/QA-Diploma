package ru.netology.page;


import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitCardPage {
    private SelenideElement heading = $$("h3").find(exactText("Оплата по карте"));
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardMonth = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardYear = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardHolder = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardCVV = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");
    private SelenideElement approvedOperation = $(byText("Операция одобрена Банком.")).parent().$("[class=\"notification__content\"]");
    private SelenideElement declinedOperation = $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class=\"notification__content\"]");
    private SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private SelenideElement cardExpirationDateError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));

    private SelenideElement requiredFieldError = $(byText("Поле обязательно для заполнения"));
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public DebitCardPage() {
        heading.shouldBe(visible);
    }

    public void inputData(DataHelper.Card card) {
        cardNumber.setValue(card.getCardNumber());
        cardMonth.setValue(card.getMonth());
        cardYear.setValue(card.getYear());
        cardHolder.setValue(card.getCardHolder());
        cardCVV.setValue(card.getCvv());
        continueButton.click();
    }

    public void waitNotificationApproved() {
        approvedOperation.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void waitNotificationDeclined() {
        declinedOperation.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void waitNotificationWrongFormat() {
        wrongFormatError.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void waitNotificationExpirationDateError() {
        cardExpirationDateError.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void waitNotificationExpiredError() {
        cardExpiredError.shouldBe(visible, Duration.ofSeconds(10));
    }


}
